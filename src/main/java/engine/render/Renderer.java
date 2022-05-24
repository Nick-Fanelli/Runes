package engine.render;

import engine.render.spirterender.SpriteRenderer;
import engine.state.State;

public class Renderer {

    private final State state;

    private final SpriteRenderer spriteRenderer;

    public Renderer(State state) {
        this.state = state;

        this.spriteRenderer = new SpriteRenderer(this, state);
    }

    public void OnCreate() {
        this.spriteRenderer.OnCreate();
    }

    public void Begin() {
        this.spriteRenderer.Begin();
    }

    public void End() {
        this.spriteRenderer.End();
    }

    public void OnDestroy() {
        this.spriteRenderer.OnDestroy();
    }

    public SpriteRenderer GetSpriteRenderer() { return this.spriteRenderer; }
}
