package org.djog_unos.tankgame.engine;

import org.joml.Vector3f;

public class Animation extends Transformable
{
    private Shader m_shader;
    private Model m_model; 
    private Texture[] m_textures;
    private int m_textureSampler;
    private float m_frameTime;

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

    public Animation(String animationDirectory, int frameCount, float frameTime, float width, float height, int textureSampler)
    {
        m_frameTime = frameTime;
        m_model = new Model(m_vertices, m_textureCoords, m_indices);
        m_shader = new Shader("texture");
        m_textures = TextureManager.getTexturesFromDir(animationDirectory, frameCount);
        transform = new Transform(new Vector3f(0,0,0), new Vector3f(width, height, 0));
        m_textureSampler = textureSampler;
    }

    public void update()
    {
        
    }

    public void draw() 
    {
        m_shader.bind();
        m_shader.setUniform("sampler", m_textureSampler);
        m_shader.setUniform("projection", transform.getProjection(Window.getMatrixProjection()));
        
        m_textures[0].bind(m_textureSampler);
        m_model.render();
    }
}