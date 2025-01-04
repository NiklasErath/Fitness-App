package edu.cc231030.MC.project.data.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises",
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String
)

