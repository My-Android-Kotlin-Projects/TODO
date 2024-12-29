package com.example.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {
    val todoaDao = MainApplication.todoDB.todoDao()
    val todoList: LiveData<List<Todo>> = todoaDao.getAllTodos().map { todos: List<Todo> -> todos.reversed() }

    fun addTodo(title: String) {

        viewModelScope.launch(Dispatchers.IO) {
            todoaDao.addTodo(
                Todo(
                    title = title,
                    createdAt = Date.from(Instant.now())
                )
            )
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoaDao.deleteTodoById(id)
        }
    }

    fun updateTodoCheckedState(id: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            todoaDao.updateTodoCheckedState(id, isChecked)
        }
    }
}