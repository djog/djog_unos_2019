package org.djog_unos.tankgame.engine;

import org.djog_unos.tankgame.game.TankGame;
import org.joml.*;

public class Shape
{
	public enum ShapeType
	{
		Triangle,
		Square
	}
	
    private Transform m_transform;
    
    private Shader m_shader;
    private Model m_model; 
    
    private static final float[] TRIANGLE_VERTICES = new float[] {
		0.0f, 0.5f, 0,      	// TOP			
        0.5f, -0.51f, 0,        // BOTTOM RIGHT    
        -0.5f, -0.5f, 0,        // BOTTOM LEFT     
    };
    private static final int[] TRIANGLE_INDICES = new int[] {
        0,1,2,
    };
        
    private static final float[] SQUARE_VERTICES = new float[] {
        -0.5f, 0.5f, 0,         // TOP RIGHT       0
        0.5f, 0.5f, 0,          // TOP LEFT        1
        0.5f, -0.5f, 0,         // BOTTOM RIGHT    2
        -0.5f, -0.5f, 0,        // BOTTOM LEFT     3
    };
    private static final int[] SQUARE_INDICES = new int[] {
        0,1,2,
        2,3,0
    };

    private Vector4f m_color; // RGBA COLOR

    public Shape(ShapeType type, float width, float height, Vector4f color)
    {
    	switch(type)
    	{
	    	case Triangle:
	    		m_model = new Model(TRIANGLE_VERTICES, TRIANGLE_INDICES);
	    		break;
	    	case Square:
	    		m_model = new Model(SQUARE_VERTICES, SQUARE_INDICES);
	    		break;
    	}
        m_color = color;
        m_shader = new Shader("color");
        m_transform = new Transform(new Vector3f(0,0,0), new Vector3f(width, height, 0));
    }

    public void setPosition(float x, float y)
    {
        m_transform.position.x = x;
        m_transform.position.y = y;
    }

    public void draw() 
    {
        m_shader.bind();
        m_shader.setUniform("color", m_color);
        m_shader.setUniform("projection", m_transform.getProjection(TankGame.PROJECTION));
        m_model.render();
    }
}