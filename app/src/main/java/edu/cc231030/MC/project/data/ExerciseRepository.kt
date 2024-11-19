package edu.cc231030.MC.project.data

import android.util.Log
import androidx.compose.ui.Modifier
import edu.cc231030.MC.project.data.db.ExerciseDao
import edu.cc231030.MC.project.data.db.ExerciseEntity
import edu.cc231030.MC.project.data.db.ExerciseSetEntity
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

    val exerciseSets: Flow<List<ExerciseSet>> = exerciseDao.getSetsForExercise()
        .map { list ->
            Log.d("ExerciseSets", "Fetched sets: $list")  // Log the raw list from the DAO
            list.map { entity ->
                ExerciseSet(entity.id, entity.exerciseId, entity.reps, entity.weight)
            }
        }


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

    suspend fun addRandomExercise() {
        exerciseDao.addExercise(
            ExerciseEntity(0, names.random())
        )

    }

    suspend fun addExerciseSet(exerciseId: Int, reps: Int, weight: Int){
        exerciseDao.addExerciseSet(
            ExerciseSetEntity(0, exerciseId, reps, weight)
        )
    }

    suspend fun deleteExerciseSet(exerciseSet: ExerciseSet){
        val entity = ExerciseSetEntity(id = exerciseSet.id, exerciseId = exerciseSet.exerciseId, reps =exerciseSet.reps, weight = exerciseSet.weight)
        exerciseDao.deleteExerciseSet(entity)
    }


    suspend fun addExercise(name: String) {
        exerciseDao.addExercise(
            ExerciseEntity(0, name)
        )
    }


    suspend fun deleteExercise(exercise: Exercise) {
        val entity = ExerciseEntity(id = exercise.id, name = exercise.name)
        exerciseDao.deleteExercise(entity)
    }
}

