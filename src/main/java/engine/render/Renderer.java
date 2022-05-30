package engine.render;

import engine.render.subrenderer.LightRenderer;
import engine.render.subrenderer.SpriteRenderer;
import engine.state.State;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL20.GL_MAX_TEXTURE_IMAGE_UNITS;

public class Renderer {

    private final State state;

    private final SpriteRenderer spriteRenderer;
    private final LightRenderer lightRenderer;

    private int gpuMaxTextureSlots;
    private int whiteTextureID = 0;

    public Renderer(State state) {
        this.state = state;

        this.spriteRenderer = new SpriteRenderer(this, state);
        this.lightRenderer = new LightRenderer(this, state);
    }

    public void OnCreate() {
        // Initialize Generics
        this.QueryGPU();
        this.CreateWhiteTexture();

        // Create Renderers
        this.spriteRenderer.OnCreate();
        this.lightRenderer.OnCreate();
    }

    public void Begin() {
        this.spriteRenderer.Begin();
        this.lightRenderer.Begin();
    }

    public void End() {
        this.spriteRenderer.End();

        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
        this.lightRenderer.End();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void OnDestroy() {
        this.spriteRenderer.OnDestroy();
        this.lightRenderer.OnDestroy();

        glDeleteTextures(whiteTextureID);
    }

    public int GetWhiteTextureID() { return this.whiteTextureID; }
    public int GetGUPMaxTextureSlots() { return this.gpuMaxTextureSlots; }

    public SpriteRenderer GetSpriteRenderer() { return this.spriteRenderer; }
    public LightRenderer GetLightRenderer() { return this.lightRenderer; }

    // Utility Methods
    private void QueryGPU() {
        // Query the GPU
        IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, intBuffer);
        gpuMaxTextureSlots = intBuffer.get(0);
    }

    private void CreateWhiteTexture() {
        whiteTextureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, whiteTextureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        int color = 0xffffffff;
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 1, 1, 0, GL_RGBA, GL_UNSIGNED_BYTE, color);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
