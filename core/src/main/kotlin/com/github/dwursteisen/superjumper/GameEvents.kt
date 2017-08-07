package com.github.dwursteisen.superjumper

object GameEvents {
    private var eventId = 1

    @JvmField
    val PLATEFORM_PULVERIZED = eventId++

    @JvmField
    val HIT_PLATFORM = eventId++

    @JvmField
    val HIT_SQUIRREL = eventId++

    @JvmField
    val HIT_SPRING = eventId++

    @JvmField
    val HIT_COIN = eventId++

    @JvmField
    val PLAYER_FALLEN = eventId++

    @JvmField
    val PLAYER_JUMPING = eventId++
}