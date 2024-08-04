package com.example.to_docompose.repositories

import com.example.to_docompose.data.ToDoDao
import com.example.to_docompose.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTask = toDoDao.getAllTasks()
    val sortByLowPriority = toDoDao.sortByLowPriority()
    val sortByHighPriority = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)

    suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTask()
    }

    fun searchDataBase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)
}