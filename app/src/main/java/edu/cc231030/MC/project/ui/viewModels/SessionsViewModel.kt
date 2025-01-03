package edu.cc231030.MC.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.data.Session
import edu.cc231030.MC.project.ui.States.SessionsUiState
import edu.cc231030.MC.project.ui.States.currentSessionUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update


class SessionsViewModel(private val repository: ExerciseRepository) : ViewModel() {

    private val _sessions = MutableStateFlow(SessionsUiState(emptyList()))
    val sessions = _sessions.asStateFlow()

    private val _currentSession = MutableStateFlow(currentSessionUiState())
    val currentSession = _currentSession.asStateFlow()

    private val _sessionTime = MutableStateFlow(0)
    val sessionTime = _sessionTime.asStateFlow()


    // job is a coroutine job - dk how to explain it better rn
    private var timerJob: Job? = null


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

    fun getSessionById(sessionId: Int) {
        viewModelScope.launch {
            val session = repository.getSessionById(sessionId)
            _currentSession.update { it.copy(currentSession = session) }
            Log.d("Sessions", "Collected Sessionsetrea: $session")


        }
    }

    fun addSession(name: String, description: String) {
        viewModelScope.launch {
            repository.addSession(name, description)
        }
    }

    fun deleteSession(sessionId: Int){
        viewModelScope.launch {
            val session = repository.getSessionById(sessionId)
            repository.deleteSession(session)
        }
    }


    // *******************************************************************

    fun addExerciseToSession(sessionId: Int, exerciseId: Int) {
        viewModelScope.launch {
            // fetch the session by Id to update the whole entity
            val session = repository.getSessionById(sessionId)
            if (session != null) {
                repository.addExerciseToSession(session, exerciseId)
            }
        }
    }

    fun deleteExerciseFromSession(sessionId: Int, exerciseId: Int) {
        viewModelScope.launch {
            // fetch the session by Id to update the whole entity
            val session = repository.getSessionById(sessionId)
            if (session != null) {
                repository.deleteExerciseFromSession(session, exerciseId)
            }
            val newSession = repository.getSessionById(sessionId)

            _currentSession.update{ it.copy(currentSession = newSession) }
        }
    }

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

