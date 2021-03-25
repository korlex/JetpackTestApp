package com.example.jetpacktestapp.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDbData(
    val name: String,
    val text: String,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)