package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {


    @Query("""
        SELECT *
        FROM question
        WHERE isDeleted = 0
    """)
    fun getAllQuestions(): Flow<List<QuestionEntity>>


    @Query("""
        SELECT *
        FROM question
        WHERE categoryId = :categoryId
        AND isDeleted = 0
    """)
    fun getQuestionsByCategory(
        categoryId: Int
    ): Flow<List<QuestionEntity>>


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


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        question: QuestionEntity
    )


    @Query("""
        DELETE FROM question
        WHERE questionId IN (:ids)
    """)
    suspend fun deleteByIds(
        ids: List<Int>
    )


    @Query("""
        UPDATE question
        SET isDeleted = 1
        WHERE questionId IN (:ids)
    """)
    suspend fun markAsDeleted(
        ids: List<Int>
    )


    @Query("""
        SELECT COUNT(*)
        FROM question
        WHERE categoryId = :categoryId
        AND isDeleted = 0
    """)
    suspend fun getQuestionCountByCategory(
        categoryId: Int
    ): Int
}