package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {

    @Query("""
    SELECT *
    FROM question
    WHERE categoryId = :categoryId
    AND isDeleted = 0
    ORDER BY RANDOM()
    LIMIT :limit
""")
    fun getRandomQuestionsByCategory(
        categoryId: Int,
        limit: Int
    ): List<QuestionEntity>

    @Query("""
        SELECT *
        FROM question
        WHERE questionId = :questionId
        AND isDeleted = 0
    """)
    suspend fun getQuestionById(
        questionId: Int
    ): QuestionEntity?

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
}