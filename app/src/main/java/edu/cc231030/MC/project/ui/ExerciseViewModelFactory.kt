package edu.cc231030.MC.project.ui

import ExerciseViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.cc231030.MC.project.data.ExerciseRepository

class ExerciseViewModelFactory(private val exerciseRepository: ExerciseRepository) : ViewModelProvider.Factory {

    // Override create method to instantiate the ViewModel with the repository
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(exerciseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}