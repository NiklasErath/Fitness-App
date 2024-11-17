package edu.cc231030.MC.project.data

import edu.cc231030.MC.project.data.db.ExerciseDao
import edu.cc231030.MC.project.data.db.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    // A predefined list of names, if needed elsewhere
    val predefinedNames = listOf(
        "Training1",
        "Training2",
        "Training3"
    )

    // Function to fetch all exercises from the database
   suspend fun findAllExercises(): Flow<List<String>> {
        return exerciseDao.findAllExercises() // Assuming this returns Flow<List<ExerciseEntity>>
            .map { entityList ->
                // Transform each ExerciseEntity to just the name
                entityList.map { it.name }
            }
    }
}
