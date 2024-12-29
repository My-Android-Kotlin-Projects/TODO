package com.example.todo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.Todo

@Dao
interface TodoDAO {
    @Query("SELECT * FROM Todo")
    fun getAllTodos(): LiveData<List<Todo>>

    @Insert
    fun addTodo(todo: Todo)

    @Query("DELETE FROM Todo WHERE id = :id")
    fun deleteTodoById(id: Int)

    @Query("UPDATE Todo SET isDone = :isChecked WHERE id = :id")
    suspend fun updateTodoCheckedState(id: Int, isChecked: Boolean)
}