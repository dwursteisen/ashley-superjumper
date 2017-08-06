package com.github.dwursteisen.superjumper

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SuperJumper : Game() {

    // used by all screens
    lateinit var batcher: SpriteBatch

    override fun create() {
        batcher = SpriteBatch()
        Settings.load()
        setScreen(LoadingScreen(this))
    }


    override fun render() {
        val gl = Gdx.gl
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f)
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()


    }

}