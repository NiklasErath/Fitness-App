import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.ExercisesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    //  hold the list of exercise descriptions
    private val _exercises = MutableStateFlow(ExercisesUiState(emptyList()))

    // public StateFlow for observing exercises
    val exercises = _exercises.asStateFlow()

    init {
        // coroutine to collect data from the repository
        viewModelScope.launch {
            repository.exercises.collect { data ->
                // update the _exercises state
                _exercises.update { oldState ->
                    oldState.copy(
                        exercises = data)
                }
            }
        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRandomContact()
        }
    }

    fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }
}

