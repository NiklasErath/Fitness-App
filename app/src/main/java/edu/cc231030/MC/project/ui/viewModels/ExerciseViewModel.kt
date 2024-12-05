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


    // ************************************************* EXERCISE

    fun addExercise(name: String, description: String) {
        viewModelScope.launch {
            repository.addExercise(name, description)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }

    fun getExerciseById(exercises: List<Int>) {
        viewModelScope.launch {
            // differnece between .map and .forEach =
            // .map is used to apply a transfromation like in this case a function
            // .forEach is used to perform an action e.g. printing values or display
           val exercise =  exercises.map{ exerciseId ->
                (repository.getExerciseById(exerciseId))}
            Log.d("Exercise", "This is an exercise: $exercise")
            _exercises.update { it.copy(exercises = exercise ) }
        }
    }

    // ************************************************* EXERCISE SET


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
