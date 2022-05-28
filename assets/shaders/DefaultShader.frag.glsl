#version 400 core

in vec4 vColor;
in float vTextureID;
in vec2 vTextureCoords;

// The !KEYWORD gets replaced at runtime
uniform sampler2D[!MAX_TEXTURE_COUNT] uTextures;

out vec4 color;

void main() {
    int textureID = int(vTextureID);

    if(textureID == 0) {
        color = vColor;
    } else {
        color = vColor * texture(uTextures[textureID], vTextureCoords);
    }
}