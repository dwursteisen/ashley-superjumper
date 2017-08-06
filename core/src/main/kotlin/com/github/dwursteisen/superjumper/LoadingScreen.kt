package com.github.dwursteisen.superjumper

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.siondream.superjumper.MainMenuScreen

class LoadingScreen(val game: SuperJumper) : ScreenAdapter() {

    private val assets = AssetManager()

    override fun show() {
        assets.load("data/background.png", Texture::class.java)
        assets.load("data/items.png", Texture::class.java)
        assets.load("data/music.mp3", Music::class.java)

        assets.load("data/music.mp3", Music::class.java)
        assets.load("data/jump.wav", Sound::class.java)
        assets.load("data/highjump.wav", Sound::class.java)
        assets.load("data/hit.wav", Sound::class.java)
        assets.load("data/coin.wav", Sound::class.java)
        assets.load("data/click.wav", Sound::class.java)

        assets.load("data/help1.png", Texture::class.java)
        assets.load("data/help2.png", Texture::class.java)
        assets.load("data/help3.png", Texture::class.java)
        assets.load("data/help4.png", Texture::class.java)
        assets.load("data/help5.png", Texture::class.java)


    }

    override fun render(delta: Float) {
        super.render(delta)

        if(assets.update()) {
            Assets.load(assets)
            game.screen = MainMenuScreen(game)
        }
    }
}