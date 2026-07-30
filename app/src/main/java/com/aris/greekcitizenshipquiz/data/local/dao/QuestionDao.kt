package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity
import com.aris.greekcitizenshipquiz.data.local.relation.QuestionWithDetails

@Dao
interface QuestionDao {

    @Query("""
    SELECT questionNumber
    FROM question
    WHERE categoryId = :categoryId
      AND isDeleted = 0
    GROUP BY questionNumber
    ORDER BY RANDOM()
    LIMIT :limit
    """)
    suspend fun getRandomQuestionGroups(
        categoryId: Int,
        limit: Int
    ): List<Int>

    @Transaction
    @Query("""
    SELECT *
    FROM question
    WHERE questionNumber IN (:groups)
      AND categoryId = :categoryId
      AND isDeleted = 0
   """)
    suspend fun getQuestionsWithOptions(
        groups: List<Int>,
        categoryId: Int
    ): List<QuestionWithDetails>

    @Transaction
    suspend fun getRandomQuestionsByCategory(
        categoryId: Int,
        limit: Int
    ): List<QuestionWithDetails> {

        val groups = getRandomQuestionGroups(
            categoryId = categoryId,
            limit = limit
        )

        return getQuestionsWithOptions(
            groups = groups,
            categoryId = categoryId
        )
    }

    ////////////////////////////////////////////////

    @Transaction
    @Query("""
        SELECT *
        FROM question
        WHERE questionId = :questionId
        AND isDeleted = 0
    """)
    suspend fun getQuestionById(
        questionId: Int
    ): QuestionWithDetails?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        questions: List<QuestionEntity>
    )

    @Query("""
    SELECT COUNT(*)
    FROM (
        SELECT questionNumber
        FROM question
        WHERE categoryId = :categoryId
        AND isDeleted = 0
        GROUP BY questionNumber
    )
    """)
    suspend fun getQuestionGroupCountByCategory(
        categoryId: Int
    ): Int

    @Transaction
    @Query("""
    SELECT *
    FROM question
    WHERE categoryId = :categoryId
    AND isDeleted = 0
    ORDER BY questionNumber ASC, createdAt ASC
""")
    suspend fun getQuestionsByCategory(
        categoryId: Int
    ): List<QuestionWithDetails>

    @Transaction
    @Query("""
    SELECT *
    FROM question
    WHERE categoryId = :categoryId
    AND typeQuestionId = :typeQuestionId
    AND isDeleted = 0
    ORDER BY questionNumber ASC, createdAt ASC
    """)
    suspend fun getQuestionsByCategoryAndType(
        categoryId: Int,
        typeQuestionId: Int
    ): List<QuestionWithDetails>
}