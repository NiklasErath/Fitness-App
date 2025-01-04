package edu.cc231030.MC.project.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseSet
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.data.db.Entities.ExerciseEntity
import edu.cc231030.MC.project.ui.States.ExercisesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import edu.cc231030.MC.project.ui.States.ExerciseSetsUiState
import edu.cc231030.MC.project.ui.States.CurrentExerciseUiState

// exercise vViewModel
class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    //  hold the list of exercise descriptions
    private val _exercises = MutableStateFlow(ExercisesUiState(emptyList()))
    private val _sets = MutableStateFlow(ExerciseSetsUiState(emptyList()))

    // public StateFlow for observing exercises
    val exercises = _exercises.asStateFlow()
    val exerciseSets = _sets.asStateFlow()

    // Stateflow for the current exercise
    private val _currentExercise = MutableStateFlow(CurrentExerciseUiState())
    val currentExercise = _currentExercise.asStateFlow()

    // get all the exercises information and update the exercises
    init {
        viewModelScope.launch {
            try {
                repository.exercises.collect { data ->
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

        // coroutine to collect exerciseSets information and update the state
        viewModelScope.launch {
            try {
                repository.exerciseSets.collect { data ->
                    _sets.update { oldState ->
                        oldState.copy(
                            exerciseSet = data,
                        )
                    }
                }
            } catch (e: Exception) {
                _sets.update { oldState -> oldState.copy() }
            }
        }
    }


    // ************************************************* EXERCISE

    // add an exercise
    fun addExercise(name: String, description: String) {
        viewModelScope.launch {
            repository.addExercise(name, description)
        }
    }

    // delete an exercise
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }

    // get List of exercises by id
    fun getExercisesById(exercises: List<Int>) {
        viewModelScope.launch {
            // difference between .map and .forEach =
            // .map is used to apply a transformation like in this case a function
            // .forEach is used to perform an action e.g. printing values or display
            val exercise = exercises.map { exerciseId ->
                (repository.getExerciseById(exerciseId))
            }
            _exercises.update { it.copy(exercises = exercise) }
        }
    }

    // get a single exercise by id
    fun getExerciseById(exerciseId: Int){
        viewModelScope.launch {
            val exercise = repository.getExerciseById(exerciseId)
            _currentExercise.update { it.copy(currentExercise = exercise) }
        }
    }

    // update an exercise
    fun updateExercise(id:Int, name: String, description: String) {
        val entity = ExerciseEntity(id = id, name = name, description = description)
        viewModelScope.launch {
            repository.updateExercise(entity)
        }
    }

    // ************************************************* EXERCISE SET

    // add a new exercise set
    fun addExerciseSet(exerciseId: Int, reps: Int, weight: Int) {
        viewModelScope.launch {
            repository.addExerciseSet(exerciseId, reps, weight)
        }
    }

    // update an exercise Set
    fun updateExerciseSet(id: Int, exerciseId: Int, reps: Int, weight: Int) {
        viewModelScope.launch {
            repository.updateExerciseSet(id, exerciseId, reps, weight)
        }
    }

    // delete an exercise Set
    fun deleteExerciseSet(exerciseSet: ExerciseSet) {
        viewModelScope.launch {
            repository.deleteExerciseSet(exerciseSet)
        }
    }


}
