package edu.cc231030.MC.project.ui.States

import edu.cc231030.MC.project.data.Exercise

data class CurrentExerciseUiState (
    val currentExercise: Exercise = Exercise(0, "", "")
)