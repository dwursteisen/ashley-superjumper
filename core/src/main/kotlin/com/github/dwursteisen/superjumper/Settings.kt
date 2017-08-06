package com.github.dwursteisen.superjumper

import com.badlogic.gdx.Gdx


object Settings {

    @JvmField
    var soundEnabled = true

    @JvmField
    val highscores = intArrayOf(100, 80, 50, 30, 10)

    private val file = ".superjumper"

    fun load() {
        try {
            val filehandle = Gdx.files.external(file)

            val strings = filehandle.readString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            soundEnabled = java.lang.Boolean.parseBoolean(strings[0])
            for (i in 0..4) {
                highscores[i] = Integer.parseInt(strings[i + 1])
            }
        } catch (e: Throwable) {
            // :( It's ok we have defaults
        }

    }

    @JvmStatic
    fun save() {
        try {
            val filehandle = Gdx.files.external(file)

            filehandle.writeString(java.lang.Boolean.toString(soundEnabled) + "\n", false)
            for (i in 0..4) {
                filehandle.writeString(Integer.toString(highscores[i]) + "\n", true)
            }
        } catch (e: Throwable) {
        }

    }

    @JvmStatic
    fun addScore(score: Int) {
        for (i in 0..4) {
            if (highscores[i] < score) {
                for (j in 4 downTo i + 1)
                    highscores[j] = highscores[j - 1]
                highscores[i] = score
                break
            }
        }
    }
}