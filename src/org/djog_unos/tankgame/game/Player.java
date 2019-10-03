package org.djog_unos.tankgame.game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.djog_unos.tankgame.engine.*;
import org.joml.*;

public class Player 
{
    private float m_movespeed = 0.000001f;
    private Model m_model; 
    private Shader m_shader;
    private Texture m_texture;

    private Matrix4f m_projection = new Matrix4f().ortho2D(-840/2, 840/2, -640/2, 640/2).scale(128); // 840 is window width 640 is window height scale 128 px
    private Transform m_transform = new Transform(new Vector3f(0,0,0), new Vector3f(16,16,1));

    private float[] m_vertices = new float[] {
        -0.5f, 0.5f, 0,         // TOP RIGHT       0
        0.5f, 0.5f, 0,          // TOP LEFT        1
        0.5f, -0.5f, 0,         // BOTTOM RIGHT    2
        -0.5f, -0.5f, 0,        // BOTTOM LEFT     3
    };

    private float[] m_textureCoords = new float[] {
        0,0,
        1,0,
        1,1,
        0,1,
    };

    private int[] m_indices = new int[] {
        0,1,2,
        2,3,0
    };

    public void init()
    {
        m_model = new Model(m_vertices, m_textureCoords, m_indices);
        m_shader = new Shader("shader");
        m_texture = new Texture("./assets/textures/pig.png");
    }

    Vector2f getInputVector(long window)
    {
        float inputH = glfwGetKey(window, GLFW_KEY_D) - glfwGetKey(window, GLFW_KEY_A);
        float inputV = glfwGetKey(window, GLFW_KEY_W) - glfwGetKey(window, GLFW_KEY_S);
        return new Vector2f(inputH, inputV);
    }

    public void update(long window)
    {
        Vector2f input = getInputVector(window);
        Vector2f movement = input;
        movement.mul(m_movespeed); // Multiply by movespeed
        if (!(movement.length() == 0.0f))
            movement.normalize();      // Normalize to prevent moving faster diagonally
        m_transform.position.add(new Vector3f(movement.x, movement.y, 0));
        System.out.println("POSITION X:" + m_transform.position.x + "  Y:" + m_transform.position.y + "  Z:" + m_transform.position.z);
    }

    public void draw() 
    {
        m_shader.bind();
        m_shader.setUniform("sampler", 0);
        // TODO: Implement position again with getProjectionMatrix function in Transform
        m_shader.setUniform("projection", m_projection);
        m_texture.bind(0);
        m_model.render();
    }
}