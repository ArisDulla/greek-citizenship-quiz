package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.IncorrectAnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncorrectAnswerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(incorrectAnswer: IncorrectAnswerEntity)

    @Query("SELECT * FROM incorrect_answers")
    fun observeAll(): Flow<List<IncorrectAnswerEntity>>

    @Query("DELETE FROM incorrect_answers WHERE questionId = :questionId")
    suspend fun delete(questionId: Int)

    @Query("DELETE FROM incorrect_answers")
    suspend fun deleteAll()
}