package edu.cc231030.MC.project.ui

import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.cc231030.MC.project.data.ExerciseRepository

class SessionViewModelFactory (private val exerciseRepository: ExerciseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionsViewModel::class.java)) {
            return SessionsViewModel(exerciseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}