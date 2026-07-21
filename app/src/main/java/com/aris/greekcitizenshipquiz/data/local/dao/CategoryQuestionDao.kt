package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.CategoryQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryQuestionDao {


    @Query("""
        SELECT *
        FROM category_question
        WHERE isActive = 1
    """)
    fun getActiveCategories(): Flow<List<CategoryQuestionEntity>>


    @Query("""
        SELECT *
        FROM category_question
        ORDER BY name ASC
    """)
    fun getAllCategories(): Flow<List<CategoryQuestionEntity>>


    @Query("""
        SELECT *
        FROM category_question
        WHERE categoryId = :categoryId
    """)
    suspend fun getCategoryById(
        categoryId: Int
    ): CategoryQuestionEntity?


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        categories: List<CategoryQuestionEntity>
    )


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        category: CategoryQuestionEntity
    )


    @Query("""
        DELETE FROM category_question
        WHERE categoryId IN (:ids)
    """)
    suspend fun deleteByIds(
        ids: List<Int>
    )


    @Query("""
        UPDATE category_question
        SET isActive = 0
        WHERE categoryId IN (:ids)
    """)
    suspend fun deactivateCategories(
        ids: List<Int>
    )


    @Query("""
        SELECT COUNT(*)
        FROM category_question
        WHERE isActive = 1
    """)
    suspend fun getActiveCategoryCount(): Int
}