#version 460

attribute vec3  vertices;

uniform mat4 projection;

void main()
{
    gl_Position = projection * vec4(vertices, 1); // Multiplied with projection
}