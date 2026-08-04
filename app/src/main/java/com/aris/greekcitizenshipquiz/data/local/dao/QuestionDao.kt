package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity
import com.aris.greekcitizenshipquiz.data.local.relation.QuestionWithDetails
import com.aris.greekcitizenshipquiz.domain.model.CategoryTypeCount

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

    @Transaction
    @Query("""
    SELECT *
    FROM question
    WHERE isNew = 1
      AND isDeleted = 0
    ORDER BY questionNumber ASC, createdAt ASC
    """)
    suspend fun getQuestionsByCategoryAndNew(): List<QuestionWithDetails>

    @Query("""
SELECT 
    tq.typeQuestionId,
    tq.name AS typeName,
    tq.description,
    tq.sortOrder,
    tq.isActive,
    tq.updatedAt,
    COUNT(q.questionId) AS count
FROM question q
INNER JOIN type_question tq
    ON q.typeQuestionId = tq.typeQuestionId
WHERE q.categoryId = :categoryId
  AND q.isDeleted = 0
GROUP BY 
    tq.typeQuestionId,
    tq.name,
    tq.description,
    tq.sortOrder,
    tq.isActive,
    tq.updatedAt
ORDER BY tq.sortOrder ASC
""")
    suspend fun getQuestionTypesByCategory(
        categoryId: Int
    ): List<CategoryTypeCount>

    @Transaction
    @Query("""
        SELECT *
        FROM question
        WHERE questionId IN (
            SELECT questionId 
            FROM incorrect_answers
        )
    """)
    suspend fun observeIncorrectQuestions(): List<QuestionWithDetails>
}