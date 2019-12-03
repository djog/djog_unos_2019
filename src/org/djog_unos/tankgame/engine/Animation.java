package org.djog_unos.tankgame.engine;

import org.joml.Vector3f;

public class Animation extends Transformable
{
    public boolean destoyed;

    private Shader m_shader;
    private Model m_model; 
    private Texture[] m_textures;
    private int m_textureSampler;
    private float m_frameTime;
    private float m_countdown;
    private int m_currentFrame = 0;
    private int m_frameCount;
    private boolean m_looping;

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

    public Animation(String animationDirectory, int frameCount, float frameTime, boolean looping, float width, float height, int textureSampler)
    {
        m_frameTime = frameTime;
        m_frameCount = frameCount;
        m_looping = looping;
        m_model = new Model(m_vertices, m_textureCoords, m_indices);
        m_shader = new Shader("texture");
        m_textures = TextureManager.getTexturesFromDir(animationDirectory, frameCount);
        transform = new Transform(new Vector3f(0,0,0), new Vector3f(width, height, 0));
        m_textureSampler = textureSampler;
        m_countdown = m_frameTime;
    }

    public void update()
    {
        m_countdown -= Game.getDeltaTime();
        if (m_countdown <= 0)
        {
            m_currentFrame++;
            m_countdown = m_frameTime;
            if (m_currentFrame >= m_frameCount)
            {
                if (m_looping)
                    m_currentFrame = 0;
                else
                    destoyed = true;
            }
        }
    }

    public void draw() 
    {
        if (destoyed)
            return;
        m_shader.bind();
        m_shader.setUniform("sampler", m_textureSampler);
        m_shader.setUniform("projection", transform.getProjection(Window.getMatrixProjection()));
        
        m_textures[m_currentFrame].bind(m_textureSampler);
        m_model.render();
    }
}