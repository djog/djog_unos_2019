package org.djog_unos.tankgame.engine;

import static org.lwjgl.opengl.GL20.*;

import java.io.*;
import java.nio.FloatBuffer;

import org.joml.*;
import org.lwjgl.BufferUtils;

public class Shader
{
    private int program;
    private int vertexShader;   // Vertex shader - goes over every vertex
    private int fragmentShader; // Fragment shader - goes over every pixel

    private final String SHADER_FOLDER = "./assets/shaders/";
    private final String VERTEX_SHADER_EXTENSION = ".vert";
    private final String FRAGMENT_SHADER_EXTENSION = ".frag";

    public Shader(String filename)
    {
        program = glCreateProgram();

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, readFile(filename + VERTEX_SHADER_EXTENSION));
        glCompileShader(vertexShader);
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) != 1)
        {   // Complie error
            System.err.println(glGetShaderInfoLog(vertexShader));
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, readFile(filename + FRAGMENT_SHADER_EXTENSION));
        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != 1)
        {   // Complie error
            System.err.println(glGetShaderInfoLog(fragmentShader));
        }

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glBindAttribLocation(program, 0, "vertices");
        glBindAttribLocation(program, 1, "textures");

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != 1)
        {   // Link error
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1)
        {   // Validate error
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    public void setUniform(String name, int value)
    {
        int location = glGetUniformLocation(program, name);
        if (location != -1)
            glUniform1i(location, value);
    }

    public void setUniform(String name, float value)
    {
        int location = glGetUniformLocation(program, name);
        if (location != -1)
            glUniform1f(location, value);
    }
    
    public void setUniform(String name, Vector2f value)
    {
        int location = glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(2); 
        value.get(buffer);
        if (location != -1)
            glUniform2fv(location, buffer);
    }
    
    public void setUniform(String name, Vector3f value)
    {
        int location = glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3); 
        value.get(buffer);
        if (location != -1)
            glUniform3fv(location, buffer);
    }
    
    public void setUniform(String name, Vector4f value)
    {
        int location = glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4); 
        value.get(buffer);
        if (location != -1)
            glUniform4fv(location, buffer);
    }

    public void setUniform(String name, Matrix4f value)
    {
        int location = glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16); 
        value.get(buffer);
        if (location != -1)
            glUniformMatrix4fv(location, false, buffer);
    }

    public void bind()
    {
        glUseProgram(program);
    }

    private String readFile(String filename)
    {
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(new File(SHADER_FOLDER + filename)));
            String line;
            while((line = br.readLine()) != null)
            {
                string.append(line);
                string.append("\n");
            }
            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return string.toString();
    }
}