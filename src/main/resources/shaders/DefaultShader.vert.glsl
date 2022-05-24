#version 400 core

layout (location = 0) in vec2 aVertexPosition;
layout (location = 1) in vec4 aColor;
layout (location = 2) in float aTextureID;
layout (location = 3) in vec2 aTextureCoords;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 vColor;

void main() {
    vColor = aColor;

    gl_Position =  uProjection * uView * vec4(aVertexPosition, 0, 1.0);
}