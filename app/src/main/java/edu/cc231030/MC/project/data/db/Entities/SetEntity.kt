package edu.cc231030.MC.project.data.db.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exerciseSets",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"], // parent table that is being referenced for the exerciseId
            childColumns = ["exerciseId"], // column that holds the reference from the parentColumn
            onDelete = ForeignKey.CASCADE // delete set when parent reference is deletec
        )
    ]
)
data class ExerciseSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseId: Int,
    val reps: Int,
    val weight: Int
)
