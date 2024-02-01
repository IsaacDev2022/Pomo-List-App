package com.pomolist.feature_task.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pomolist.feature_task.domain.model.Converters
import com.pomolist.feature_task.domain.model.Pomodoro
import com.pomolist.feature_task.domain.model.Task

@Database(entities = [Task::class, Pomodoro::class], version = 11)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract val taskDAO: TaskDAO
    abstract val pomodoroDAO: PomodoroDAO

    companion object {
        const val DATABASE_NAME = "pomolistdb_3"
    }
}