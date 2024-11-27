package edu.cc231030.MC.project.data.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import edu.cc231030.MC.project.data.db.Converters

@TypeConverters(Converters::class)
@Entity(tableName = "sessions")
data class SessionEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val exercises: List<Int> = emptyList()
)