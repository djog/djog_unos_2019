package org.djog_unos.tankgame.engine;

import org.joml.*;

public class Shape
{
	public enum ShapeType
	{
		Triangle,
        Square,
        Circle
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

    private static final float[] CIRCLE_VERTICES = new float[] {
        -0.00000f, -0.000000f, -0.000000f,
        -0.00000f, -0.500000f, -0.000000f,
        -0.09754f, -0.490393f, -0.000000f,
        -0.19134f, -0.461940f, -0.000000f,
        -0.27778f, -0.415735f, -0.000000f,
        -0.35355f, -0.353554f, -0.000000f,
        -0.41573f, -0.277785f, -0.000000f,
        -0.46194f, -0.191342f, -0.000000f,
        -0.49039f, -0.097545f, -0.000000f,
        -0.50000f, -0.000000f, -0.000000f,
        -0.49039f, 0.097545f,  -0.000000f,
        -0.46194f, 0.191341f,  -0.000000f,
        -0.41573f, 0.277785f,  -0.000000f,
        -0.35355f, 0.353553f,  -0.000000f,
        -0.27778f, 0.415735f,  -0.000000f,
        -0.19134f, 0.461940f,  -0.000000f,
        -0.09754f, 0.490393f,  -0.000000f,
        0.000000f, 0.500000f,  -0.000000f,
        0.097545f, 0.490392f,  -0.000000f,
        0.191342f, 0.461940f,  -0.000000f,
        0.277785f, 0.415735f,  -0.000000f,
        0.353554f, 0.353553f,  -0.000000f,
        0.415735f, 0.277785f,  -0.000000f,
        0.461940f, 0.191341f,  -0.000000f,
        0.490393f, 0.097545f,  -0.000000f,
        0.500000f, -0.000001f, -0.000000f,
        0.490392f, -0.097546f, -0.000000f,
        0.461939f, -0.191342f, -0.000000f,
        0.415734f, -0.277786f, -0.000000f,
        0.353553f, -0.353554f, -0.000000f,
        0.277784f, -0.415735f, -0.000000f,
        0.191341f, -0.461940f, -0.000000f,
        0.097544f, -0.490393f, -0.000000f,
    };
    private static final int[] CIRCLE_INDICES = new int[] {
        0,1,2,
        0,2,3,
        0,3,4,
        0,4,5,
        0,5,6,
        0,6,7,
        0,7,8,
        0,8,9,
        0,9,10,
        0,10,11,
        0,11,12,
        0,12,13,
        0,13,14,
        0,14,15,
        0,15,16,
        0,16,17,
        0,17,18,
        0,18,19,
        0,19,20,
        0,20,21,
        0,21,22,
        0,22,23,
        0,23,24,
        0,24,25,
        0,25,26,
        0,26,27,
        0,27,28,
        0,28,29,
        0,29,30,
        0,30,31,
        0,31,1,
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
            case Circle:
	    		m_model = new Model(CIRCLE_VERTICES, CIRCLE_INDICES);
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

    public void setPosition(Vector2f position)
    {
        m_transform.position.x = position.x;
        m_transform.position.y = position.y;
    }

    public void setSize(float x, float y)
    {
        m_transform.scale = new Vector3f(x, y, 1);
    }

    public void draw() 
    {
        m_shader.bind();
        m_shader.setUniform("color", m_color);
        m_shader.setUniform("projection", m_transform.getProjection(Window.getMatrixProjection()));
        m_model.render();
    }
}