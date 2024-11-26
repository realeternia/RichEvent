package com.example.realcorp

data class Event(
    val id: Int = 0,
    val name: String = "",
    val ip: String = "",
    val port: Int = 0
)

data class EvtCfg(
    val events: List<Event> = emptyList()
)