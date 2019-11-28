package org.djog_unos.tankgame.engine;

import org.djog_unos.tankgame.game.*;

public class ViewManager
{
    private static View m_currentView;

    public enum ViewType
    {
        Menu,
        Game
    }

    public void startDefaultView()
    {
        setView(getDefaultViewType());
    }
    
    public static void setView(ViewType viewType)
    {
        if (m_currentView != null)
            m_currentView.endView();
        m_currentView = makeView(viewType);
        m_currentView.setupView();
    }

    public void update()
    {
        m_currentView.updateView();
    }

    public void draw()
    {
        m_currentView.drawView();
    }

    static View makeView(ViewType viewType)
    {
        switch(viewType)
        {
            case Menu:
                return new MenuView();
            case Game:
                return new GameView();
            default:
                throw new RuntimeException("The ViewType " + viewType.toString() + " is not implemented in makeView()!");
        }
    }

    static ViewType getDefaultViewType()
    {
        return ViewType.Menu;
    }
}