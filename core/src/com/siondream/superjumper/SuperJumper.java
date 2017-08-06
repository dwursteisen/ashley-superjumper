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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SuperJumper extends Game {
	// used by all screens
	public SpriteBatch batcher;

	private final AssetManager assets = new AssetManager();

	@Override
	public void create () {
		batcher = new SpriteBatch();
		Settings.load();

		assets.load("data/background.png", Texture.class);
		assets.load("data/items.png", Texture.class);
		assets.load("data/music.mp3", Music.class);

		assets.load("data/music.mp3", Music.class);
		assets.load("data/jump.wav", Sound.class);
		assets.load("data/highjump.wav", Sound.class);
		assets.load("data/hit.wav", Sound.class);
		assets.load("data/coin.wav", Sound.class);
		assets.load("data/click.wav", Sound.class);

		assets.finishLoading();
		assets.load("data/help1.png", Texture.class);
		assets.load("data/help2.png", Texture.class);
		assets.load("data/help3.png", Texture.class);
		assets.load("data/help4.png", Texture.class);
		assets.load("data/help5.png", Texture.class);

		Assets.load(assets);
		setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}
}
