package edu.cc231030.MC.project.data.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises",
  /*  foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"], // parent table that is being referenced for the exerciseId
            childColumns = ["sessionId"], // column that holds the reference from the parentColumn
            onDelete = androidx.room.ForeignKey.CASCADE // delete set when parent reference is deletec
        )
    ]*/
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    //val sessionId: Int,
)

