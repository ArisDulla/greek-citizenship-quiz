package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.QuestionOptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionOptionDao {


    @Query("""
        SELECT *
        FROM question_option
        WHERE questionId = :questionId
        ORDER BY `order` ASC
    """)
    fun getOptionsByQuestion(
        questionId: Int
    ): Flow<List<QuestionOptionEntity>>


    @Query("""
        SELECT *
        FROM question_option
        WHERE questionId = :questionId
        ORDER BY `order` ASC
    """)
    suspend fun getOptionsByQuestionOnce(
        questionId: Int
    ): List<QuestionOptionEntity>


    @Query("""
        SELECT *
        FROM question_option
        WHERE optionId = :optionId
    """)
    suspend fun getOptionById(
        optionId: Int
    ): QuestionOptionEntity?


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        options: List<QuestionOptionEntity>
    )


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        option: QuestionOptionEntity
    )


    @Query("""
        DELETE FROM question_option
        WHERE questionId IN (:questionIds)
    """)
    suspend fun deleteByQuestionIds(
        questionIds: List<Int>
    )


    @Query("""
        DELETE FROM question_option
        WHERE optionId IN (:optionIds)
    """)
    suspend fun deleteByIds(
        optionIds: List<Int>
    )


    @Query("""
        SELECT COUNT(*)
        FROM question_option
        WHERE questionId = :questionId
    """)
    suspend fun getOptionCount(
        questionId: Int
    ): Int
}