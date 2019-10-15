package org.djog_unos.tankgame.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Model
{
    private int drawCount;
    private int vId; // Vertex id
    private int tId; // Texture id
    private int iId; // Indice id

    private final int SIZE = 3; // 2 for XY 3 for XYZ
    
    private boolean hasTexture;
    
    public Model(float[] vertices, int[] indices)
    {
    	hasTexture = false;
		drawCount = indices.length;
        
        // Vertex buffer
        vId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);    
             

        // Indice buffer
        iId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
        IntBuffer buffer =  BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); // Unbind
        glBindBuffer(GL_ARRAY_BUFFER, 0); // Unbind 
    }
    public Model(float[] vertices, float[] textureCoords, int[] indices)
    {
    	hasTexture = true;
        drawCount = indices.length;
        
        // Vertex buffer
        vId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);    
        
    	// Texture buffer
        tId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(textureCoords), GL_STATIC_DRAW);

        // Indice buffer
        iId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
        IntBuffer buffer =  BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); // Unbind
        glBindBuffer(GL_ARRAY_BUFFER, 0); // Unbind 
    }

    public void render()
    {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glVertexAttribPointer(0, SIZE, GL_FLOAT, false, 0, 0);
        
        if (hasTexture)
        {
        	 glBindBuffer(GL_ARRAY_BUFFER, tId);
             glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0); // Size is 2, textures are always 2D
        }

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId);
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); // Unbind
        glBindBuffer(GL_ARRAY_BUFFER, 0); // Unbind

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    private FloatBuffer createBuffer(float[] vertices)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
        return buffer;
    }
}