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
                Exercise(entity.id, entity.name) // Map to Exercise model
            }
        }

    val exerciseSets: Flow<List<ExerciseSet>> = exerciseDao.getSetsForExercise()
        .map { list ->
            Log.d("ExerciseSets", "Fetched sets: $list")  // Log the raw list from the DAO
            list.map { entity ->
                ExerciseSet(entity.id, entity.exerciseId, entity.reps, entity.weight)
            }
        }

    suspend fun addRandomExercise() {
        exerciseDao.addExercise(
            ExerciseEntity(0, names.random())
        )

    }

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

    // *********************************************************************

    suspend fun addExercise(name: String) {
        exerciseDao.addExercise(
            ExerciseEntity(0, name)
        )
    }


    suspend fun deleteExercise(exercise: Exercise) {
        val entity = ExerciseEntity(id = exercise.id, name = exercise.name)
        exerciseDao.deleteExercise(entity)
    }

    // *********************************************

    val sessions: Flow<List<Session>> = exerciseDao.getAllSessions()
        .map { list ->
            list.map { entity ->
                Session(entity.id, entity.name)
            }
        }


    suspend fun addRandomSession() {
        exerciseDao.addSession(
            SessionEntity(0, names.random())
        )

    }

    suspend fun addSession(name: String) {
        exerciseDao.addSession(
            SessionEntity(0, name)
        )
    }

    suspend fun deleteSession(session: Session) {
        val entity = SessionEntity(id = session.id, name = session.name)
        exerciseDao.deleteSession(entity)
    }

}
//***************************************************************

/*
    fun getExerciseSetsForExercise(exerciseId: Int): Flow<List<ExerciseSet>> {
        return exerciseDao.getSetsForExercise(exerciseId)
            .map { list ->
                list.map { entity ->
                    ExerciseSet(
                        id = entity.id,
                        exerciseId = entity.exerciseId,
                        reps = entity.reps,
                        weight = entity.weight
                    )
                }
            }
    }
*/

/*
    suspend fun getSetsforExercise(exerciseId: Int, reps: Int, weight: Int) {
        exerciseDao.getSetsForExercise(
            ExerciseSetEntity(0, exerciseId, reps, weight)
        )
    }


    suspend fun getSetsforExercise(exerciseId: Int) {
        exerciseDao.getSetsForExercise(exerciseId)
    }
*/
