package com.example.todo

import android.app.Application
import androidx.room.Room
import com.example.todo.db.TodoDatabase

class MainApplication : Application() {
    companion object {
        lateinit var todoDB: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDB = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }
}