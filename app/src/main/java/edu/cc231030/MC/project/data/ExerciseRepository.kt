package edu.cc231030.MC.project.data

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
            list.map { entity ->
                Exercise(entity.id, entity.name) // Map to Exercise model
            }
        }


    val exerciseSets: Flow<List<ExerciseSet>> = exerciseDao.getSetsForExercise(1)
        .map { list ->
            list.map { entity ->
                ExerciseSet(entity.id, entity.exerciseId, entity.reps, entity.weight) // Map to ExerciseSet model
            }
        }



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

