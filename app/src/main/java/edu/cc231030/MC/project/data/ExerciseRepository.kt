package edu.cc231030.MC.project.data

import android.util.Log
import edu.cc231030.MC.project.data.db.ExerciseDao
import edu.cc231030.MC.project.data.db.Entities.ExerciseEntity
import edu.cc231030.MC.project.data.db.Entities.ExerciseSetEntity
import edu.cc231030.MC.project.data.db.Entities.SessionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepository(private val exerciseDao: ExerciseDao) {


    val names = listOf(
        "Training1",
        "Training2",
        "Training3"
    )

    // get all exercises
    val exercises: Flow<List<Exercise>> = exerciseDao.findAllExercises()
        .map { list ->
            Log.d("ExerciseSets", "Fetched exercises: $list")  // Log the raw list from the DAO

            list.map { entity ->
                Exercise(entity.id, entity.name, entity.description) // Map to Exercise model
            }
        }

    val exerciseSets: Flow<List<ExerciseSet>> = exerciseDao.getSetsForExercise()
        .map { list ->
            Log.d("ExerciseSets", "Fetched sets: $list")  // Log the raw list from the DAO
            list.map { entity ->
                ExerciseSet(entity.id, entity.exerciseId, entity.reps, entity.weight)
            }
        }

    // ******************************************** EXERCISE SET

    suspend fun addExerciseSet(exerciseId: Int, reps: Int, weight: Int) {
        exerciseDao.addExerciseSet(
            ExerciseSetEntity(0, exerciseId, reps, weight)
        )
    }

    suspend fun deleteExerciseSet(exerciseSet: ExerciseSet) {
        val entity = ExerciseSetEntity(
            id = exerciseSet.id,
            exerciseId = exerciseSet.exerciseId,
            reps = exerciseSet.reps,
            weight = exerciseSet.weight
        )
        exerciseDao.deleteExerciseSet(entity)
    }

    suspend fun updateExerciseSet(id: Int, exerciseId: Int, reps: Int, weight: Int) {
        val entity = ExerciseSetEntity(id, exerciseId, reps, weight)
        exerciseDao.updateExerciseSet(entity)
    }

    // ********************************************************************* EXERCISE

    suspend fun addExercise(name: String, description: String) {
        exerciseDao.addExercise(
            ExerciseEntity(0, name, description)
        )
    }


    suspend fun deleteExercise(exercise: Exercise) {
        val entity = ExerciseEntity(id = exercise.id, name = exercise.name, description = exercise.description)
        exerciseDao.deleteExercise(entity)
    }

    suspend fun getExerciseById(exerciseId: Int): Exercise {
        return exerciseDao.getExerciseById(exerciseId).let { entity ->
            Exercise(
                id = entity.id,
                name = entity.name,
                description = entity.description,
            )
        }
    }


    // ********************************************* SESSION

    val sessions: Flow<List<Session>> = exerciseDao.getAllSessions()
        .map { list ->
            list.map { entity ->
                Session(entity.id, entity.name,entity.exercises, entity.description)
            }
        }

    suspend fun addSession(name: String, description: String) {
        exerciseDao.addSession(
            SessionEntity(0, name, emptyList(), description)
        )
    }

    suspend fun deleteSession(session: Session) {
        val entity = SessionEntity(id = session.id, name = session.name, description = session.description)
        exerciseDao.deleteSession(entity)
    }


    suspend fun getSessionById(sessionId: Int): Session {
        return exerciseDao.getSessionById(sessionId).let { entity ->
            Session(
                id = entity.id,
                name = entity.name,
                exercises = entity.exercises,
                description = entity.description
            )
        }
    }

    suspend fun addExerciseToSession(session: Session, exerciseId: Int) {
        // make shure an exercise isn't twice in the list
        if (exerciseId !in session.exercises) {
            // update the list of exercises
            val updatedExercises = session.exercises.toMutableList().apply { add(exerciseId) }
            // val updated Entity to update the Entity
            val updatedEntity = SessionEntity(
                id = session.id,
                name = session.name,
                exercises = updatedExercises,
                description = session.description
            )
            exerciseDao.updateSession(
                updatedEntity
            )
        }
    }

    suspend fun deleteExerciseFromSession(session: Session, exerciseId: Int) {
        // make shure an exercise isn't twice in the list
        Log.d("delete", "try to delete $exerciseId in Session $session")
        if (exerciseId in session.exercises) {
            // update the list of exercises
            val updatedExercises = session.exercises.toMutableList().apply { remove(exerciseId) }
            // val updated Entity to update the Entity
            val updatedEntity = SessionEntity(
                id = session.id,
                name = session.name,
                exercises = updatedExercises,
                description = session.description
            )
            exerciseDao.updateSession(
                updatedEntity
            )

        }
    }

}
//***************************************************************


