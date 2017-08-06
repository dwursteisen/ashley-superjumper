/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.siondream.superjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @deprecated "Use AssetsManager instead"
 */
@Deprecated()
public class Assets {


    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture items;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScoresRegion;
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion arrow;
    public static TextureRegion pause;
    public static TextureRegion spring;
    public static TextureRegion castle;
    public static Animation<TextureRegion> coinAnim;
    public static Animation<TextureRegion> bobJump;
    public static Animation<TextureRegion> bobFall;
    public static Animation<TextureRegion> bobHit;
    public static Animation<TextureRegion> squirrelFly;
    public static Animation<TextureRegion> platform;
    public static Animation<TextureRegion> breakingPlatform;
    public static BitmapFont font;

    public static Music music;
    public static Sound jumpSound;
    public static Sound highJumpSound;
    public static Sound hitSound;
    public static Sound coinSound;
    public static Sound clickSound;

    public static AssetManager assetManager;

    public static void load(AssetManager assets) {
        assetManager = assets;

        background = assets.get("data/background.png", Texture.class);
        backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

        items = assets.get("data/items.png", Texture.class);
        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
        pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
        ready = new TextureRegion(items, 320, 224, 192, 32);
        gameOver = new TextureRegion(items, 352, 256, 160, 96);
        highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
        logo = new TextureRegion(items, 0, 352, 274, 142);
        soundOff = new TextureRegion(items, 0, 0, 64, 64);
        soundOn = new TextureRegion(items, 64, 0, 64, 64);
        arrow = new TextureRegion(items, 0, 64, 64, 64);
        pause = new TextureRegion(items, 64, 64, 64, 64);

        spring = new TextureRegion(items, 128, 0, 32, 32);
        castle = new TextureRegion(items, 128, 64, 64, 64);
        coinAnim = new Animation<TextureRegion>(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
                new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));
        bobJump = new Animation<TextureRegion>(0.2f, new TextureRegion(items, 0, 128, 32, 32), new TextureRegion(items, 32, 128, 32, 32));
        bobFall = new Animation<TextureRegion>(0.2f, new TextureRegion(items, 64, 128, 32, 32), new TextureRegion(items, 96, 128, 32, 32));
        bobHit = new Animation<TextureRegion>(0.2f, new TextureRegion(items, 128, 128, 32, 32));
        squirrelFly = new Animation<TextureRegion>(0.2f, new TextureRegion(items, 0, 160, 32, 32), new TextureRegion(items, 32, 160, 32, 32));
        platform = new Animation<TextureRegion>(0.2f, new TextureRegion(items, 64, 160, 64, 16));
        breakingPlatform = new Animation<TextureRegion>(0.2f, new TextureRegion(items, 64, 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
                new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items, 64, 208, 64, 16));

        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

        music = assets.get("data/music.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        if (Settings.soundEnabled) music.play();
        jumpSound = assets.get("data/jump.wav", Sound.class);
        highJumpSound = assets.get("data/highjump.wav", Sound.class);
        hitSound = assets.get("data/hit.wav", Sound.class);
        coinSound = assets.get("data/coin.wav", Sound.class);
        clickSound = assets.get("data/click.wav", Sound.class);

        coinAnim.setPlayMode(PlayMode.LOOP);
        bobJump.setPlayMode(PlayMode.LOOP);
        bobFall.setPlayMode(PlayMode.LOOP);
        bobHit.setPlayMode(PlayMode.LOOP);
        squirrelFly.setPlayMode(PlayMode.LOOP);
        platform.setPlayMode(PlayMode.LOOP);
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }

    public static Texture loadTexture(String filename) {
        assetManager.finishLoadingAsset(filename);
        return assetManager.get(filename, Texture.class);
    }
}