package org.djog_unos.tankgame.game;

import static org.lwjgl.glfw.GLFW.*;
import org.djog_unos.tankgame.engine.*;
import org.joml.*;

public class Player implements IGameObject
{
    private float m_movespeed = 2f;
    private Shader m_shader;

    private Matrix4f m_projection = new Matrix4f().ortho2D(-TankGame.WINDOW_WIDTH/2, TankGame.WINDOW_WIDTH/2, -TankGame.WINDOW_HEIGHT/2, TankGame.WINDOW_HEIGHT/2); 
    private Transform m_transform = new Transform(new Vector3f(0,0,0), new Vector3f(128, 128, 0));
    private Vector2f m_input;

    private Model m_model; 
    private Texture m_texture;

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

    public void input()
    {
        m_input = getInputVector(Game.getWindow());
    }

    Vector2f getInputVector(long window)
    {
        float inputH = glfwGetKey(window, GLFW_KEY_D) - glfwGetKey(window, GLFW_KEY_A);
        float inputV = glfwGetKey(window, GLFW_KEY_W) - glfwGetKey(window, GLFW_KEY_S);
        return new Vector2f(inputH, inputV);
    }

    public void update()
    {
        Vector2f movement = m_input;
        if (!(movement.length() == 0.0f))
            movement.normalize();      // Normalize to prevent moving faster diagonally
        movement.mul(m_movespeed * (float)Game.getDeltaTime()); // Multiply by movespeed and deltatime
        m_transform.position.add(new Vector3f(movement.x, movement.y, 0));
    }

    public void draw() 
    {
        m_shader.bind();
        m_shader.setUniform("sampler", 0);
        m_shader.setUniform("projection", m_transform.getProjection(m_projection));
        m_texture.bind(0);
        m_model.render();
    }
}