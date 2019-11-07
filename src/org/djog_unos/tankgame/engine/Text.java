package org.djog_unos.tankgame.engine;

import org.joml.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fontMeshCreator.*;

public class Text
{
    private static Shader m_shader = new Shader("font");

    public static void draw(GUIText text) 
    {
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glDisable(GL11.GL_DEPTH_TEST);
    	
        m_shader.bind();
        m_shader.setUniform("colour", new Vector3f(1.0f, 1.0f, 1.0f));
        m_shader.setUniform("fontAtlas", 0);
        m_shader.setUniform("translation", new Vector2f(0.0f, 0.0f));
        
        GL30.glBindVertexArray(text.getMesh());
    	GL20.glEnableVertexAttribArray(0);
    	GL20.glEnableVertexAttribArray(1);
    	GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL30.glBindVertexArray(0);
    	
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
    
}