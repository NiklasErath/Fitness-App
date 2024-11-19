import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseSet

import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.ExercisesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import edu.cc231030.MC.project.ui.ExerciseSetsUiState


class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    //  hold the list of exercise descriptions
    private val _exercises = MutableStateFlow(ExercisesUiState(emptyList()))
    private val _sets = MutableStateFlow(ExerciseSetsUiState(emptyList()))

    // public StateFlow for observing exercises
    val exercises = _exercises.asStateFlow()
    val sets= _sets.asStateFlow()

    init {
        // coroutine to collect data from the repository
        viewModelScope.launch {
            // Start with loading state
            _exercises.update { it.copy() }

            try {
                repository.exercises.collect { data ->
                    // update the _exercises state with fetched data
                    _exercises.update { oldState ->
                        oldState.copy(
                            exercises = data,
                        )
                    }
                }

                repository.exerciseSets.collect{ data ->
                    _sets.update {
                        oldState ->
                        oldState.copy(
                            exerciseSet = data,
                        )
                    }

                }
            } catch (e: Exception) {
                // Handle error, e.g., network failure
                _exercises.update { oldState ->
                    oldState.copy(
                    )
                }
                _sets.update { oldState ->
                    oldState.copy()
                }
            }
        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRandomExercise()
        }
    }

    fun addExercise(name: String) {
        viewModelScope.launch {
            repository.addExercise(name)
        }
    }

    fun addExerciseSet(exerciseId: Int, reps: Int, weight: Int){
        viewModelScope.launch {
            repository.addExerciseSet(exerciseId, reps, weight)
        }
    }

    fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }
}

