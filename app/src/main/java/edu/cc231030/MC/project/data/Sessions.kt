package edu.cc231030.MC.project.data

data class Session (
    val id: Int,
    val name: String,
    val exercises: List<Int> = emptyList(),
    val description: String,
)