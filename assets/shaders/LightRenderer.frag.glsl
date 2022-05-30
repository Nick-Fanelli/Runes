#version 400 core

in vec2 vRelVertexPosition;
in vec4 vColor;

out vec4 color;

void main() {
    float distance = length(vRelVertexPosition);

    color = vec4(vColor.rgb, vColor.a * (pow(0.01, distance) - 0.01));
}