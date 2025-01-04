package edu.cc231030.MC.project.data

import android.util.Log
import edu.cc231030.MC.project.data.db.ExerciseDao
import edu.cc231030.MC.project.data.db.Entities.ExerciseEntity
import edu.cc231030.MC.project.data.db.Entities.ExerciseSetEntity
import edu.cc231030.MC.project.data.db.Entities.SessionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    // get all exercises
    val exercises: Flow<List<Exercise>> = exerciseDao.findAllExercises()
        .map { list ->
            Log.d("ExerciseSets", "Fetched exercises: $list")  // Log the raw list from the DAO

            list.map { entity ->
                Exercise(entity.id, entity.name, entity.description) // Map to Exercise model
            }
        }

    // get all exerciseSets
    val exerciseSets: Flow<List<ExerciseSet>> = exerciseDao.getSetsForExercise()
        .map { list ->
            Log.d("ExerciseSets", "Fetched sets: $list")  // Log the raw list from the DAO
            list.map { entity ->
                ExerciseSet(entity.id, entity.exerciseId, entity.reps, entity.weight)
            }
        }

    // ******************************************** EXERCISE SET

    // add a new exerciseSet
    suspend fun addExerciseSet(exerciseId: Int, reps: Int, weight: Int) {
        exerciseDao.addExerciseSet(
            ExerciseSetEntity(0, exerciseId, reps, weight)
        )
    }

    // delete an exerciseSet
    suspend fun deleteExerciseSet(exerciseSet: ExerciseSet) {
        val entity = ExerciseSetEntity(
            id = exerciseSet.id,
            exerciseId = exerciseSet.exerciseId,
            reps = exerciseSet.reps,
            weight = exerciseSet.weight
        )
        exerciseDao.deleteExerciseSet(entity)
    }

    // update an exercise Set with new data
    suspend fun updateExerciseSet(id: Int, exerciseId: Int, reps: Int, weight: Int) {
        val entity = ExerciseSetEntity(id, exerciseId, reps, weight)
        exerciseDao.updateExerciseSet(entity)
    }

    // ********************************************************************* EXERCISE

    // add/create an exercise
    suspend fun addExercise(name: String, description: String) {
        exerciseDao.addExercise(
            ExerciseEntity(0, name, description)
        )
    }

    // delete an exercise
    suspend fun deleteExercise(exercise: Exercise) {
        val entity = ExerciseEntity(id = exercise.id, name = exercise.name, description = exercise.description)
        exerciseDao.deleteExercise(entity)
    }

    // get an exercise by id
    suspend fun getExerciseById(exerciseId: Int): Exercise {
        return exerciseDao.getExerciseById(exerciseId).let { entity ->
            Exercise(
                id = entity.id,
                name = entity.name,
                description = entity.description,
            )
        }
    }

    // update an exercise with new information/data
    suspend fun updateExercise(exerciseEntity: ExerciseEntity){
        exerciseDao.updateExercise(exerciseEntity)
    }


    // ********************************************* SESSION

    // create Flow List with all the sessions
    val sessions: Flow<List<Session>> = exerciseDao.getAllSessions()
        .map { list ->
            list.map { entity ->
                Session(entity.id, entity.name,entity.exercises, entity.description)
            }
        }

    // add/create a Session
    suspend fun addSession(name: String, description: String) {
        exerciseDao.addSession(
            SessionEntity(0, name, emptyList(), description)
        )
    }

    // delete Session
    suspend fun deleteSession(session: Session) {
        val entity = SessionEntity(id = session.id, name = session.name, description = session.description)
        exerciseDao.deleteSession(entity)
    }

    // get a Session by id
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

    // add an exercise to a session
    suspend fun addExerciseToSession(session: Session, exerciseId: Int) {
        // check if an exercise isn't twice in the list
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

    // delete an exercise from a session by id - delete the id out of the array and update the session
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


