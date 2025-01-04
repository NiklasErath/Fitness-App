package edu.cc231030.MC.project.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.cc231030.MC.project.data.db.Entities.ExerciseEntity
import edu.cc231030.MC.project.data.db.Entities.ExerciseSetEntity
import edu.cc231030.MC.project.data.db.Entities.SessionEntity
import kotlinx.coroutines.flow.Flow

//Data Access object with SQL operations/functions

@Dao
interface ExerciseDao {

    @Insert
    suspend fun addExercise(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM exercises")
    fun findAllExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getExerciseById(id: Int): ExerciseEntity

    @Update
    suspend fun updateExercise(exerciseEntity: ExerciseEntity)

    //*********************************************************** SETS

    @Query("SELECT * FROM exerciseSets")
    fun getSetsForExercise(): Flow<List<ExerciseSetEntity>>

    @Insert
    suspend fun addExerciseSet(exerciseSetEntity: ExerciseSetEntity)

    @Delete
    suspend fun deleteExerciseSet(exerciseSetEntity: ExerciseSetEntity)

    @Update
    suspend fun updateExerciseSet(exerciseSetEntity: ExerciseSetEntity)

    //*********************************************************** SESSION

    @Insert
    suspend fun addSession(sessionEntity: SessionEntity)

    @Delete
    suspend fun deleteSession(sessionEntity: SessionEntity)

    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Update
    suspend fun updateSession(sessionEntity: SessionEntity)

    @Query("SELECT * FROM sessions WHERE id = :id")
    suspend fun getSessionById(id: Int): SessionEntity


}