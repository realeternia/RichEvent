package com.example.realcorp

data class Event(
    val id: Int = 0,
    val type: String = "",
    val name: String = "",
    val pic: String = "",
    val mp3: String = "",
    var reset: Boolean = false
)

data class EvtCfg(
    val events: List<Event> = emptyList()
)