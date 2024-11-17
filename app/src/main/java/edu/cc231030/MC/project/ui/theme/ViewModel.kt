package edu.cc231030.MC.project.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModel : ViewModel() {

    // Create a MutableStateFlow and expose it as an immutable StateFlow
    private val _message = MutableStateFlow("Hello, StateFlow!")
    val message: StateFlow<String> = _message.asStateFlow()
}
