#version 440

uniform sampler2D sampler;

varying vec2 texCoords;

out vec4 FragColor;

void main()
{
    FragColor = texture2D(sampler, texCoords);
}