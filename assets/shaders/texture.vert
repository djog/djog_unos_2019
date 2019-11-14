#version 130

attribute vec3  vertices;
attribute vec2 textures;

varying vec2 texCoords;

uniform mat4 projection;

void main()
{
    texCoords = textures;
    gl_Position = projection * vec4(vertices, 1); // Multiplied with projection
}