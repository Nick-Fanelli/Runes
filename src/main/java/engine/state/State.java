package engine.state;

import engine.core.display.WindowResizeEvent;
import engine.render.Camera;
import engine.render.Renderer;
import org.joml.Vector2f;

import java.util.ArrayList;

public abstract class State {

    public final Camera camera;

    private final Renderer renderer;

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    public State() {
        this.renderer = new Renderer(this);
        this.camera = new Camera(new Vector2f(0.0f, 0.0f));
    }

    public void OnCreate() {
        this.renderer.OnCreate();
    }

    public void OnUpdate(float deltaTime) {
        this.renderer.Begin();

        for(GameObject gameObject : gameObjects) {
            gameObject.OnUpdate(deltaTime);
        }

        this.renderer.End();
    }

    public void OnDestroy() {
        this.renderer.OnDestroy();
    }

    public void OnWindowResize(WindowResizeEvent event) {
        this.camera.UpdateAspectRatio(event.aspectRation);
    }

    public GameObject CreateGameObject() {
        GameObject gameObject = new GameObject(this);
        this.gameObjects.add(gameObject);
        return gameObject;
    }

    public Renderer GetRenderer() { return this.renderer; }

}
