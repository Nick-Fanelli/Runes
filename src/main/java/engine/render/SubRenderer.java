package engine.render;

import engine.render.Renderer;
import engine.state.State;

public abstract class SubRenderer {

    protected final Renderer renderer;
    protected final State state;

    public SubRenderer(Renderer renderer, State state) {
        this.renderer = renderer;
        this.state = state;
    }

    public abstract void OnCreate();

    public abstract void Begin();
    public abstract void End();

    public abstract void OnDestroy();

}
