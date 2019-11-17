package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class MenuView extends View
{
    private float m_countdown = 3.0f;
    private Sprite m_sprite;

    @Override
    protected void setupView() {
        m_sprite = new Sprite("menu_placeholder.png", 1024, 1024, 0);
    }

    @Override
    protected void updateView() {
        m_countdown -= Game.getDeltaTime();
        if (m_countdown <= 0.0f)
            ViewManager.setView(ViewManager.ViewType.Game);
    }

    @Override
    protected void drawView() {
        m_sprite.draw();
    }

}