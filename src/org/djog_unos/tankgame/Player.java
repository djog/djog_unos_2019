package org.djog_unos.tankgame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

class Player
{
    private float m_x = 0;
    private float m_y = 0;
    private Texture m_texture;

    void init()
    {
		m_texture = new Texture("./assets/textures/pig.png");
    }

    private void manageInput(long window)
    {
        if(glfwGetKey(window, GLFW_KEY_A) == 1) m_x -= 0.001;
        if(glfwGetKey(window, GLFW_KEY_D) == 1) m_x += 0.001;
        if(glfwGetKey(window, GLFW_KEY_W) == 1) m_y += 0.001;
        if(glfwGetKey(window, GLFW_KEY_S) == 1) m_y -= 0.001;
    }

    void update(long win)
    {
        manageInput(win);
    }

    void draw()
    {
        // TODO: Implement m_x & m_y again 
        m_texture.bind();
        glBegin(GL_QUADS);
            glColor4f(1, 1, 0, 0);
            glTexCoord2f(0, 0);
            glVertex2f(-0.5f, 0.5f);

            glColor4f(1, 0, 1, 0);
            glTexCoord2f(1, 0);
            glVertex2f(0.5f, 0.5f);

            glColor4f(0, 1, 1, 0);
            glTexCoord2f(1, 1);
            glVertex2f(0.5f, -0.5f);

            glColor4f(0, 1, 0, 0);
            glTexCoord2f(0, 1);
            glVertex2f(-0.5f, -0.5f);
        glEnd();
    }
}