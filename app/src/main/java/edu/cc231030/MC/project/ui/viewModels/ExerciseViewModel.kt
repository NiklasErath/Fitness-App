package edu.cc231030.MC.project.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseSet

import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.States.ExercisesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import edu.cc231030.MC.project.ui.States.ExerciseSetsUiState


class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    //  hold the list of exercise descriptions
    private val _exercises = MutableStateFlow(ExercisesUiState(emptyList()))
    private val _sets = MutableStateFlow(ExerciseSetsUiState(emptyList()))

    // public StateFlow for observing exercises
    val exercises = _exercises.asStateFlow()
    val exerciseSets = _sets.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.exercises.collect { data ->
                    Log.d("ExerciseSets", "Collected exercises: $data")  // Log the collected data
                    _exercises.update { oldState ->
                        oldState.copy(
                            exercises = data,
                        )
                    }
                }
            } catch (e: Exception) {
                _exercises.update { oldState -> oldState.copy() }
            }
        }

        // coroutine to collect exerciseSets data
        viewModelScope.launch {
            try {
                repository.exerciseSets.collect { data ->
                    Log.d("ExerciseSets", "Collected sets: $data")  // Log the collected data
                    _sets.update { oldState ->
                        oldState.copy(
                            exerciseSet = data,
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle error for exerciseSets flow
                _sets.update { oldState -> oldState.copy() }
            }
        }
    }


    /*
    fun getSetsforExercise(exerciseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSetsforExercise(exerciseId)
        }
    }
*/


    fun onAddButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRandomExercise()
        }
    }

    // *************************************************

    fun addExercise(name: String) {
        viewModelScope.launch {
            repository.addExercise(name)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }

    // *************************************************


    fun addExerciseSet(exerciseId: Int, reps: Int, weight: Int) {
        viewModelScope.launch {
            repository.addExerciseSet(exerciseId, reps, weight)
        }
    }

    fun updateExerciseSet(id: Int, exerciseId: Int, reps: Int, weight: Int){
        viewModelScope.launch {
            repository.updateExerciseSet(id, exerciseId, reps, weight)
        }
    }

    fun deleteExerciseSet(exerciseSet: ExerciseSet) {
        viewModelScope.launch {
            repository.deleteExerciseSet(exerciseSet)
        }
    }



}
