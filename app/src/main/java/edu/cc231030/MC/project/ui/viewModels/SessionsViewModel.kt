package edu.cc231030.MC.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.States.SessionsUiState
import edu.cc231030.MC.project.ui.States.CurrentSessionUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

// viewModel for the Sessions S
class SessionsViewModel(private val repository: ExerciseRepository) : ViewModel() {

    // Stateflow for sessions
    private val _sessions = MutableStateFlow(SessionsUiState(emptyList()))
    val sessions = _sessions.asStateFlow()

    // Stateflow for the current Session
    private val _currentSession = MutableStateFlow(CurrentSessionUiState())
    val currentSession = _currentSession.asStateFlow()

    // Timer for the Workout duration
    private val _sessionTime = MutableStateFlow(0)
    val sessionTime = _sessionTime.asStateFlow()


    // job is a coroutine job - dk how to explain it better rn
    private var timerJob: Job? = null

    // get all the sessions and their information
    init {
        viewModelScope.launch {
            try {
                repository.sessions.collect { data ->
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

    // get Session data by id
    fun getSessionById(sessionId: Int) {
        viewModelScope.launch {
            val session = repository.getSessionById(sessionId)
            _currentSession.update { it.copy(currentSession = session) }

        }
    }

    // add / create a new session
    fun addSession(name: String, description: String) {
        viewModelScope.launch {
            repository.addSession(name, description)
        }
    }

    // delete a session
    fun deleteSession(sessionId: Int){
        viewModelScope.launch {
            val session = repository.getSessionById(sessionId)
            repository.deleteSession(session)
        }
    }


    // ******************************************************************* EXERCISE SESSION

    // add an exercise to a Session by id
    fun addExerciseToSession(sessionId: Int, exerciseId: Int) {
        viewModelScope.launch {
            // fetch the session by Id to update the whole entity
            val session = repository.getSessionById(sessionId)
                repository.addExerciseToSession(session, exerciseId)
        }
    }

    // delete an exercise from a session by id
    fun deleteExerciseFromSession(sessionId: Int, exerciseId: Int) {
        viewModelScope.launch {
            // fetch the session by Id to update the whole entity
            val session = repository.getSessionById(sessionId)
                repository.deleteExerciseFromSession(session, exerciseId)
            val newSession = repository.getSessionById(sessionId)

            _currentSession.update{ it.copy(currentSession = newSession) }
        }
    }


    // ***************************************************** TIMER

    // timer functions
    fun startTimer() {
        timerJob?.cancel() // Cancel any existing timer
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000) // Delay for 1 second
                _sessionTime.value++ // Increment time
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel() // Stops the timer
    }
}

