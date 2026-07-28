package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.CategoryQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryQuestionDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        categories: List<CategoryQuestionEntity>
    )

    @Query("""
        DELETE FROM category_question
        WHERE categoryId IN (:ids)
    """)
    suspend fun deleteByIds(
        ids: List<Int>
    )

    @Query("""
    SELECT *
    FROM category_question c
    WHERE c.isActive = 1
      AND EXISTS (
          SELECT 1
          FROM question q
          WHERE q.categoryId = c.categoryId
            AND q.isDeleted = FALSE
      )
""")
    fun getActiveCategoriesWithQuestions(): Flow<List<CategoryQuestionEntity>>
}