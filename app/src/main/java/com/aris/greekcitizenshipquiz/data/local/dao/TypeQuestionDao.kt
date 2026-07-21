package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.TypeQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeQuestionDao {


    @Query("""
        SELECT *
        FROM type_question
        WHERE isActive = 1
        ORDER BY sortOrder ASC
    """)
    fun getActiveTypes(): Flow<List<TypeQuestionEntity>>


    @Query("""
        SELECT *
        FROM type_question
        ORDER BY sortOrder ASC
    """)
    fun getAllTypes(): Flow<List<TypeQuestionEntity>>


    @Query("""
        SELECT *
        FROM type_question
        WHERE typeQuestionId = :typeQuestionId
    """)
    suspend fun getTypeById(
        typeQuestionId: Int
    ): TypeQuestionEntity?


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        types: List<TypeQuestionEntity>
    )


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        type: TypeQuestionEntity
    )


    @Query("""
        DELETE FROM type_question
        WHERE typeQuestionId IN (:ids)
    """)
    suspend fun deleteByIds(
        ids: List<Int>
    )


    @Query("""
        UPDATE type_question
        SET isActive = 0
        WHERE typeQuestionId IN (:ids)
    """)
    suspend fun deactivateTypes(
        ids: List<Int>
    )


    @Query("""
        SELECT COUNT(*)
        FROM type_question
        WHERE isActive = 1
    """)
    suspend fun getActiveTypeCount(): Int
}