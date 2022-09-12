package com.example.todolist.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelDao {
    @Insert
    fun insertModel(info: Info)
    @Delete
    fun deleteModel(info: Info)
    @Update
    fun updateModel(info: Info)
    @Query("select * from Info")
    fun getAllModel(): Flow<List<Info>>
}