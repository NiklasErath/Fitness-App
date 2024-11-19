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

    @Query("SELECT * FROM exerciseSets WHERE exerciseId = :exerciseId")
    fun getSetsForExercise(exerciseId: Int): Flow<List<ExerciseSetEntity>>

    @Insert
    suspend fun addExerciseSet(exerciseSetEntity: ExerciseSetEntity)


/*
    // Get all sets for a specific exercise
    @Query("SELECT * FROM sets WHERE exerciseId = :exerciseId")
    fun getSetsForExercise(exerciseId: Int): List<SetEntity>

    // Get all exercises with their sets
    @Transaction
    @Query("SELECT * FROM exercises")
    fun findAllExercisesWithSets(): Flow<List<ExerciseWithSets>>

 */
}