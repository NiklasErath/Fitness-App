package edu.cc231030.MC.project.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

class ViewModel() : ViewModel() {

    private val _message = MutableStateFlow("Welcome to the Training App!")
    val message: StateFlow<String> = _message

    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }
}
