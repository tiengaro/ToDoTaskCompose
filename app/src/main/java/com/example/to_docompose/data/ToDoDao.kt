package com.example.to_docompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.util.Constants.DATABASE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM $DATABASE_TABLE ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM $DATABASE_TABLE WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM $DATABASE_TABLE")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM $DATABASE_TABLE WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM $DATABASE_TABLE ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM $DATABASE_TABLE ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): Flow<List<ToDoTask>>
}