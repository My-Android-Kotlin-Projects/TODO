package com.example.todo

import java.time.Instant
import java.util.Date

object TodoManager {
    private val todoList = mutableListOf<Todo>()

    fun addTodo(title: String) {
        todoList.add(
            Todo(
                id = todoList.size + 1,
                title = title,
                createdAt = Date.from(Instant.now())
            )
        )
    }

    fun getTodoList(): List<Todo> {
        return todoList
    }

    fun deleteTodo(id: Int) {
        todoList.removeIf { it.id == id }
    }
}