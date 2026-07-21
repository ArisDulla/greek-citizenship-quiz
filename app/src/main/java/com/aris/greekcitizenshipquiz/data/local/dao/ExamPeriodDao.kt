package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.ExamPeriodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamPeriodDao {


    @Query("""
        SELECT *
        FROM exam_period
        WHERE isActive = 1
    """)
    fun getActiveExamPeriods(): Flow<List<ExamPeriodEntity>>


    @Query("""
        SELECT *
        FROM exam_period
        ORDER BY title ASC
    """)
    fun getAllExamPeriods(): Flow<List<ExamPeriodEntity>>


    @Query("""
        SELECT *
        FROM exam_period
        WHERE id = :id
    """)
    suspend fun getExamPeriodById(
        id: Int
    ): ExamPeriodEntity?


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        periods: List<ExamPeriodEntity>
    )


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        period: ExamPeriodEntity
    )


    @Query("""
        DELETE FROM exam_period
        WHERE id IN (:ids)
    """)
    suspend fun deleteByIds(
        ids: List<Int>
    )


    @Query("""
        UPDATE exam_period
        SET isActive = 0
        WHERE id IN (:ids)
    """)
    suspend fun deactivatePeriods(
        ids: List<Int>
    )


    @Query("""
        SELECT COUNT(*)
        FROM exam_period
        WHERE isActive = 1
    """)
    suspend fun getActivePeriodCount(): Int
}