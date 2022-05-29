package engine.state;

import engine.core.display.WindowResizeEvent;
import engine.render.Camera;
import engine.render.Renderer;
import org.joml.Vector2f;

import java.util.ArrayList;

public abstract class State {

    public final Camera camera;

    protected final Renderer renderer;

    public State() {
        this.renderer = new Renderer(this);
        this.camera = new Camera(new Vector2f(0.0f, 0.0f));
    }

    public void OnCreate() {
        this.renderer.OnCreate();
    }

    public abstract void OnUpdate(float deltaTime);
    public abstract void OnRender();

    public void OnDestroy() {
        this.renderer.OnDestroy();
    }

    public void OnWindowResize(WindowResizeEvent event) {
        this.camera.UpdateAspectRatio(event.aspectRation);
    }

    public Renderer GetRenderer() { return this.renderer; }

}
