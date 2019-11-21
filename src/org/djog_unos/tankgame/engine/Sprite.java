package org.djog_unos.tankgame.engine;

import org.joml.*;

public class Sprite
{
    private Transform m_transform;
    
    private Shader m_shader;
    private Model m_model; 
    private Texture m_texture;
    private int m_textureSampler;

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

    public Sprite(String textureName, float width, float height, int textureSampler)
    {
        m_model = new Model(m_vertices, m_textureCoords, m_indices);
        m_shader = new Shader("texture");
        m_texture = TextureManager.getTexture(textureName);
        m_transform = new Transform(new Vector3f(0,0,0), new Vector3f(width, height, 0));
        m_textureSampler = textureSampler;
    }
    
    // Setters
    
    public void setPosition(float x, float y)
    {
        m_transform.position.x = x;
        m_transform.position.y = y;
    }
    
    public void setRotation(float z)
    {
        m_transform.rotation = z;
    }
    
    public void setScale(float width, float height)
    {
    	m_transform.scale = new Vector3f(width, height, 0);
    }
    
    // Getters
    public Vector2f getPosition() { return new Vector2f(m_transform.position.x, m_transform.position.y); }
    public float getRotation() { return m_transform.rotation; }
    public float getWidth() { return m_transform.scale.x; }
    public float getHeight() { return m_transform.scale.y; }
    
    public void draw() 
    {
        m_shader.bind();
        m_shader.setUniform("sampler", m_textureSampler);
        m_shader.setUniform("projection", m_transform.getProjection(Window.getMatrixProjection()));

        m_texture.bind(m_textureSampler);
        m_model.render();
    }

}