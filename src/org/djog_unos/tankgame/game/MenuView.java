package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;
import org.djog_unos.tankgame.engine.audio.*;

public class MenuView extends View
{
    private Sprite m_sprite;

    @Override
    protected void setupView() {
        AudioManager.setMusic(new SoundBuffer("menu_music.ogg"));
        
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

    }
}