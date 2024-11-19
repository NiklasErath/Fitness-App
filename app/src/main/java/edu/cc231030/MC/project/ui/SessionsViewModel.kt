import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.util.Log
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionsUiState
import kotlinx.coroutines.flow.update


class SessionsViewModel(private val repository: ExerciseRepository) : ViewModel() {

    private val _sessions = MutableStateFlow(SessionsUiState(emptyList()))
    val sessions = _sessions.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.sessions.collect { data ->
                    Log.d("Sessions", "Collected Sessions: $data")
                    _sessions.update { oldState ->
                        oldState.copy(
                            sessions = data,
                        )
                    }
                }
            } catch (e: Exception) {
                _sessions.update { oldState -> oldState.copy() }
            }
        }
    }

    fun addSession(name: String) {
        viewModelScope.launch {
            repository.addSession(name)
        }
    }

    fun addRandomSession() {
        viewModelScope.launch {
            repository.addRandomSession()
        }
    }
}
