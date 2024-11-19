package edu.cc231030.MC.project.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.cc231030.MC.project.data.ExerciseSet
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
/*
    @Query("SELECT * FROM exerciseSets WHERE exerciseId = :exerciseId")
    fun getSetsForExercise(exerciseId: Int): Flow<List<ExerciseSetEntity>>
*/
    @Query("SELECT * FROM exerciseSets")
    fun getSetsForExercise(): Flow<List<ExerciseSetEntity>>

    @Insert
    suspend fun addExerciseSet(exerciseSetEntity: ExerciseSetEntity)

    @Delete
    suspend fun deleteExerciseSet(exerciseSetEntity: ExerciseSetEntity)

    @Update
    suspend fun  updateExerciseSet(exerciseSetEntity: ExerciseSetEntity)

}