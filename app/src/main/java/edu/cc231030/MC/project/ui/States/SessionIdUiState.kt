package edu.cc231030.MC.project.ui.States

import edu.cc231030.MC.project.data.Session

data class currentSessionUiState (
    val currentSession: Session = Session(0, "", emptyList())
)