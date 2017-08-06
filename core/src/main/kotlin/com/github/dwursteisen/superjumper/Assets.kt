package com.github.dwursteisen.superjumper


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion

object Assets {


    lateinit var background: Texture
    lateinit var backgroundRegion: TextureRegion

    lateinit var items: Texture
    lateinit var mainMenu: TextureRegion
    lateinit var pauseMenu: TextureRegion
    lateinit var ready: TextureRegion
    lateinit var gameOver: TextureRegion
    lateinit var highScoresRegion: TextureRegion
    lateinit var logo: TextureRegion
    lateinit var soundOn: TextureRegion
    lateinit var soundOff: TextureRegion
    lateinit var arrow: TextureRegion
    lateinit var pause: TextureRegion
    lateinit var spring: TextureRegion
    lateinit var castle: TextureRegion

    lateinit var coinAnim: Animation<TextureRegion>
    lateinit var bobJump: Animation<TextureRegion>
    lateinit var bobFall: Animation<TextureRegion>
    lateinit var bobHit: Animation<TextureRegion>
    lateinit var squirrelFly: Animation<TextureRegion>
    lateinit var platform: Animation<TextureRegion>
    lateinit var breakingPlatform: Animation<TextureRegion>
    lateinit var font: BitmapFont;

    lateinit var music: Music
    lateinit var jumpSound: Sound
    lateinit var highJumpSound: Sound
    lateinit var hitSound: Sound
    lateinit var coinSound: Sound
    lateinit var clickSound: Sound

    lateinit var assetManager: AssetManager

    @JvmStatic
    fun load(assets: AssetManager) {
        assetManager = assets;

        background = assets["data/background.png"]
        backgroundRegion = TextureRegion(background, 0, 0, 320, 480)

        items = assets["data/items.png"]
        mainMenu = TextureRegion(items, 0, 224, 300, 110)
        pauseMenu = TextureRegion(items, 224, 128, 192, 96)
        ready = TextureRegion(items, 320, 224, 192, 32)
        gameOver = TextureRegion(items, 352, 256, 160, 96)
        highScoresRegion = TextureRegion(Assets.items, 0, 257, 300, 110 / 3)
        logo = TextureRegion(items, 0, 352, 274, 142)
        soundOff = TextureRegion(items, 0, 0, 64, 64)
        soundOn = TextureRegion(items, 64, 0, 64, 64)
        arrow = TextureRegion(items, 0, 64, 64, 64)
        pause = TextureRegion(items, 64, 64, 64, 64)

        spring = TextureRegion(items, 128, 0, 32, 32)
        castle = TextureRegion(items, 128, 64, 64, 64)
        coinAnim = Animation <TextureRegion>(0.2f, TextureRegion(items, 128, 32, 32, 32), TextureRegion(items, 160, 32, 32, 32),
                TextureRegion(items, 192, 32, 32, 32), TextureRegion(items, 160, 32, 32, 32))
        bobJump = Animation <TextureRegion>(0.2f, TextureRegion(items, 0, 128, 32, 32), TextureRegion(items, 32, 128, 32, 32))
        bobFall = Animation <TextureRegion>(0.2f, TextureRegion(items, 64, 128, 32, 32), TextureRegion(items, 96, 128, 32, 32))
        bobHit = Animation <TextureRegion>(0.2f, TextureRegion(items, 128, 128, 32, 32))
        squirrelFly = Animation <TextureRegion>(0.2f, TextureRegion(items, 0, 160, 32, 32), TextureRegion(items, 32, 160, 32, 32))
        platform = Animation <TextureRegion>(0.2f, TextureRegion(items, 64, 160, 64, 16))
        breakingPlatform = Animation <TextureRegion>(0.2f, TextureRegion(items, 64, 160, 64, 16), TextureRegion(items, 64, 176, 64, 16),
                TextureRegion(items, 64, 192, 64, 16), TextureRegion(items, 64, 208, 64, 16))

        font = BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false)

        music = assets["data/music.mp3"]
        music.setLooping(true)
        music.setVolume(0.5f)
        if (Settings.soundEnabled) music.play()
        jumpSound = assets["data/jump.wav"]
        highJumpSound = assets["data/highjump.wav"]
        hitSound = assets["data/hit.wav"]
        coinSound = assets["data/coin.wav"]
        clickSound = assets["data/click.wav"]

        coinAnim.setPlayMode(PlayMode.LOOP)
        bobJump.setPlayMode(PlayMode.LOOP)
        bobFall.setPlayMode(PlayMode.LOOP)
        bobHit.setPlayMode(PlayMode.LOOP)
        squirrelFly.setPlayMode(PlayMode.LOOP)
        platform.setPlayMode(PlayMode.LOOP)
    }

    @JvmStatic
    fun playSound(sound: Sound) {
        if (Settings.soundEnabled) sound.play(1f)
    }

    @JvmStatic
    fun loadTexture(filename: String): Texture {
        assetManager.finishLoadingAsset(filename)
        return assetManager[filename]
    }
}