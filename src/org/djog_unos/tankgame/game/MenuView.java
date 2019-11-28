package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;
import org.djog_unos.tankgame.engine.audio.*;

public class MenuView extends View
{
    private Sprite m_sprite;
    private SoundSource m_musicSource;

    @Override
    protected void setupView() {
        // Setup game music
	//	SoundBuffer soundBuffer = new SoundBuffer("heavy_fire.ogg");
    //	AudioManager.addSoundBuffer(soundBuffer);
	//	m_musicSource = new SoundSource(false, false);
	//	m_musicSource.setBuffer(soundBuffer.getBufferId());
	//	AudioManager.addSoundSource("menu_music", m_musicSource);
     //   AudioManager.playSoundSource("menu_music");
        
        m_sprite = new Sprite("menu_placeholder.png", 1024, 1024, 0);
    }

    @Override
    protected void updateView() {
        if (InputManager.isMouseButtonDown(0))
            ViewManager.setView(ViewManager.ViewType.Game);
    }

    @Override
    protected void drawView() {
        m_sprite.draw();
    }

    @Override
    protected void endView() {
//        m_musicSource.stop();
    }
}