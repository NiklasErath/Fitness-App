package edu.cc231030.MC.project.data

import edu.cc231030.MC.project.data.db.ExerciseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    val name = listOf(
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
}
