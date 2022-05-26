#version 400 core

layout (location = 0) in vec2 aVertexPosition;
layout (location = 1) in vec4 aColor;
layout (location = 2) in float aTextureID;
layout (location = 3) in vec2 aTextureCoords;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 vColor;
out float vTextureID;
out vec2 vTextureCoords;

void main() {
    vColor = aColor;
    vTextureID = aTextureID;
    vTextureCoords = aTextureCoords;

    gl_Position =  uProjection * uView * vec4(aVertexPosition, 0, 1.0);
}