package edu.cc231030.MC.project.data.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises")
    fun findAllExercises(): Flow<List<ExerciseEntity>>
}