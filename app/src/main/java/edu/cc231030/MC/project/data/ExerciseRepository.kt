package edu.cc231030.MC.project.data

import edu.cc231030.MC.project.data.db.ExerciseDao
import edu.cc231030.MC.project.data.db.ExerciseEntity
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

    suspend fun addRandomContact() {
        exerciseDao.addExercise(
            ExerciseEntity(0, names.random())
        )

    }

    suspend fun deleteExercise(exercise: Exercise) {
        val entity = ExerciseEntity(id = exercise.id, name = exercise.name)
        exerciseDao.deleteExercise(entity)
    }
}

