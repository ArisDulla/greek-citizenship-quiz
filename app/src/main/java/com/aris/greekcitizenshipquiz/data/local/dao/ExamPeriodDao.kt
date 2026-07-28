package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.ExamPeriodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamPeriodDao {


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        periods: List<ExamPeriodEntity>
    )

    @Query("""
    SELECT title
    FROM exam_period
    ORDER BY updatedAt DESC
    LIMIT 1
""")
    fun observeLatestExamPeriodTitle(): Flow<String?>
}