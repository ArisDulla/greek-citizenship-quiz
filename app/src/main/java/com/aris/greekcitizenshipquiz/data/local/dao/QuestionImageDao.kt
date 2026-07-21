package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionImageDao {


    @Query("""
        SELECT *
        FROM question_image
        WHERE questionId = :questionId
        ORDER BY `order` ASC
    """)
    fun getImagesByQuestion(
        questionId: Int
    ): Flow<List<QuestionImageEntity>>


    @Query("""
        SELECT *
        FROM question_image
        WHERE questionId = :questionId
        ORDER BY `order` ASC
    """)
    suspend fun getImagesByQuestionOnce(
        questionId: Int
    ): List<QuestionImageEntity>


    @Query("""
        SELECT *
        FROM question_image
        WHERE imageId = :imageId
    """)
    suspend fun getImageById(
        imageId: Int
    ): QuestionImageEntity?


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        images: List<QuestionImageEntity>
    )


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        image: QuestionImageEntity
    )


    @Query("""
        DELETE FROM question_image
        WHERE questionId IN (:questionIds)
    """)
    suspend fun deleteByQuestionIds(
        questionIds: List<Int>
    )


    @Query("""
        DELETE FROM question_image
        WHERE imageId IN (:imageIds)
    """)
    suspend fun deleteByIds(
        imageIds: List<Int>
    )


    @Query("""
        SELECT COUNT(*)
        FROM question_image
        WHERE questionId = :questionId
    """)
    suspend fun getImageCount(
        questionId: Int
    ): Int
}