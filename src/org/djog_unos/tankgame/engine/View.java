package org.djog_unos.tankgame.engine;

public abstract class View
{
    protected abstract void setupView();
    protected abstract void updateView();
    protected abstract void drawView();
    protected abstract void endView();
}