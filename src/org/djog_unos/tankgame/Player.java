package org.djog_unos.tankgame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player{
    private float m_x = 0;
    private float m_y = 0;

    public void update(long win){
        System.out.println(m_x);
        manage_input(win);
        draw();
    }

    private void manage_input(long win){
        if(glfwGetKey(win, GLFW_KEY_A) == 1) m_x -= 0.001;
        if(glfwGetKey(win, GLFW_KEY_D) == 1) m_x += 0.001;
        if(glfwGetKey(win, GLFW_KEY_W) == 1) m_y += 0.001;
        if(glfwGetKey(win, GLFW_KEY_S) == 1) m_y -= 0.001;
    }

    private void draw(){
        glBegin(GL_QUADS);
            glColor4f(1, 0, 0, 0);
            glVertex2f(m_x - 0.5f, m_y + 0.5f);

            glColor4f(0, 1, 0, 0);
            glVertex2f(m_x + 0.5f, m_y + 0.5f);

            glColor4f(0, 0, 1, 0);
            glVertex2f(m_x + 0.5f, m_y - 0.5f);

            glColor4f(1, 1, 1, 0);
            glVertex2f(m_x - 0.5f, m_y - 0.5f);
        glEnd();
    }
}
