package edu.cc231030.MC.project.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//Data Access object

@Dao
interface ExerciseDao {

    @Insert
    suspend fun addExercise(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM exercises")
    fun findAllExercises(): Flow<List<ExerciseEntity>>
}