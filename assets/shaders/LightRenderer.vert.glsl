#version 400 core

layout (location = 0) in vec2 aVertexPosition;
layout (location = 1) in vec2 aRelVertexPosition;
layout (location = 2) in vec4 aColor;

uniform mat4 uProjection;
uniform mat4 uView;

out vec2 vRelVertexPosition;
out vec4 vColor;

void main() {
    vRelVertexPosition = aRelVertexPosition;
    vColor = aColor;

    gl_Position = uProjection * uView * vec4(aVertexPosition, 0.0, 1.0);
}