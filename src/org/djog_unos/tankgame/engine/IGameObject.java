package org.djog_unos.tankgame.engine;

public interface IGameObject
{
    default public void init() { }
    default public void update() { }
    public void draw();
}