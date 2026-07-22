package com.aris.greekcitizenshipquiz.data.repository

import androidx.room.withTransaction
import com.aris.greekcitizenshipquiz.data.local.AppDatabase
import com.aris.greekcitizenshipquiz.data.local.dao.CategoryQuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.ExamPeriodDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionImageDao
import com.aris.greekcitizenshipquiz.data.local.dao.QuestionOptionDao
import com.aris.greekcitizenshipquiz.data.local.dao.SyncMetaDao
import com.aris.greekcitizenshipquiz.data.local.dao.TypeQuestionDao
import com.aris.greekcitizenshipquiz.data.remote.util.ZipExtractor
import com.aris.greekcitizenshipquiz.data.remote.api.QuizApi
import com.aris.greekcitizenshipquiz.data.remote.dto.CategoryQuestionDto
import com.aris.greekcitizenshipquiz.data.remote.dto.ExamPeriodDto
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionImageDto
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionOptionDto
import com.aris.greekcitizenshipquiz.data.remote.dto.TypeQuestionDto
import com.aris.greekcitizenshipquiz.data.remote.parser.JsonParser
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import com.aris.greekcitizenshipquiz.data.remote.dto.QuestionDto
import com.aris.greekcitizenshipquiz.data.remote.dto.VersionDto
import com.aris.greekcitizenshipquiz.data.mapper.toEntity
import android.util.Log

@Singleton
class QuizRepository @Inject constructor(

    private val quizApi: QuizApi,

    private val jsonParser: JsonParser,

    private val appDatabase: AppDatabase,

    private val questionDao: QuestionDao,

    private val questionOptionDao: QuestionOptionDao,

    private val questionImageDao: QuestionImageDao,

    private val categoryQuestionDao: CategoryQuestionDao,

    private val typeQuestionDao: TypeQuestionDao,

    private val examPeriodDao: ExamPeriodDao,

    private val syncMetaDao: SyncMetaDao

) {


    suspend fun syncQuizData(): Result<Unit> {

        Log.d("SYNC", "Repository started")

        return try {

            // 1. Get current local sync version

            val currentMeta = syncMetaDao.getSyncMeta()

            val currentVersion = 1
               // currentMeta?.version ?: 1 play store ++++++ usesCleartextTraffic

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            // 2. Download ZIP file from Django API

            val response =
                quizApi.downloadQuiz(
                    version = currentVersion
                )
            if (response.code() == 204) {

                Log.d(
                    "SYNC",
                    "No updates available"
                )

                return Result.success(Unit)
            }
            if (!response.isSuccessful) {
                return Result.failure(
                    Exception(
                        "Sync failed: ${response.code()}"
                    )
                )
            }

            // 3. Save ZIP temporarily in device storage
            val body =
                response.body()
                    ?: return Result.failure(
                        Exception("Empty response")
                    )
            val zipFile =
                saveZipTemporarily(body)

            // 4. Extract JSON files from ZIP
            val files =
                ZipExtractor.extractZip(zipFile)

            // 5. Parse JSON files into DTO objects -----------------------------
            val versionJson =
                files["version.json"]
                    ?: return Result.failure(
                        Exception("Missing version.json")
                    )

            val versionDto: VersionDto? =
                jsonParser.parseObject(
                    versionJson,
                    VersionDto::class.java
                )

            val newVersion =
                versionDto
                    ?: return Result.failure(
                        Exception("Missing version data")
                    )

            // -------------------------
            val questionsJson =
                files["questions.json"]
                    ?: return Result.failure(
                        Exception("Missing questions.json")
                    )
            val questions: List<QuestionDto> =
                jsonParser.parseList(
                    questionsJson,
                    QuestionDto::class.java
                )
            // ------------------------
            val optionsJson =
                files["options.json"]
                    ?: return Result.failure(
                        Exception("Missing options.json")
                    )
            val options: List<QuestionOptionDto> =
                jsonParser.parseList(
                    optionsJson,
                    QuestionOptionDto::class.java
                )
            // -----------------------
            val imagesJson =
                files["images.json"]
                    ?: return Result.failure(
                        Exception("Missing images.json")
                    )
            val images: List<QuestionImageDto> =
                jsonParser.parseList(
                    imagesJson,
                    QuestionImageDto::class.java
                )
            // ----------------------
            val categoriesJson =
                files["categories.json"]
                    ?: return Result.failure(
                        Exception("Missing categories.json")
                    )
            val categories: List<CategoryQuestionDto> =
                jsonParser.parseList(
                    categoriesJson,
                    CategoryQuestionDto::class.java
                )
            // --------------------
            val typesJson =
                files["types.json"]
                    ?: return Result.failure(
                        Exception("Missing types.json")
                    )
            val types: List<TypeQuestionDto> =
                jsonParser.parseList(
                    typesJson,
                    TypeQuestionDto::class.java
                )
            // --------------------
            val examPeriodsJson =
                files["exam_periods.json"]
                    ?: return Result.failure(
                        Exception("Missing exam_periods.json")
                    )
            val examPeriods: List<ExamPeriodDto> =
                jsonParser.parseList(
                    examPeriodsJson,
                    ExamPeriodDto::class.java
                )
            // --------------------

            // --------------------

            // Log parsed JSON data
            Log.d(
                "SYNC",
                """
            PARSE COMPLETED

            Version:
            ${newVersion.version}
            ${newVersion.updatedAt}

            Categories:
            ${categories.size}

            Types:
            ${types.size}

            Questions:
            ${questions.size}

            Options:
            ${options.size}

            Images:
            ${images.size}

            Exam Periods:
            ${examPeriods.size}
            """.trimIndent()
            )

            // --------------------
            //
            // Execute all database updates safely within a transaction.
            // If an error occurs, the transaction is rolled back.
            //
            appDatabase.withTransaction {


                categoryQuestionDao.insertAll(
                    categories.map {
                        it.toEntity()
                    }
                )


                typeQuestionDao.insertAll(
                    types.map {
                        it.toEntity()
                    }
                )


                questionDao.insertAll(
                    questions.map {
                        it.toEntity()
                    }
                )


                questionOptionDao.insertAll(
                    options.map {
                        it.toEntity()
                    }
                )


                questionImageDao.insertAll(
                    images.map {
                        it.toEntity()
                    }
                )


                examPeriodDao.insertAll(
                    examPeriods.map {
                        it.toEntity()
                    }
                )


                syncMetaDao.saveSyncMeta(
                    newVersion.toEntity()
                )
                Log.d(
                    "SYNC",
                    "Database insert completed"
                )

            }
            if (zipFile.exists()) {
                zipFile.delete()
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Log.e("SYNC", "Sync failed", e)
            Result.failure(e)
        }
    }

    private fun saveZipTemporarily(
        body: ResponseBody
    ): File {

        // Create temporary ZIP file
        val file =
            File.createTempFile(
                "quiz_update",
                ".zip"
            )


        file.outputStream().use { output ->

            body.byteStream()
                .use { input ->

                    input.copyTo(output)
                }
        }

        Log.d(
            "SYNC",
            "ZIP SIZE = ${file.length()}"
        )

        return file
    }

}