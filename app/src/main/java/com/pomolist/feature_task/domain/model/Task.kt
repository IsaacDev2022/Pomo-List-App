package com.pomolist.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String = "",
    val description: String = "",
    val priority: String = "",
    val date: String = "",
    val time: String = "",
    var minutes: Long = 0,
    var seconds: Long = 0,
    var amount: Long = 0,
    var completed: Boolean = false
)