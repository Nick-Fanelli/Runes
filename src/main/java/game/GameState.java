package game;

import engine.asset.AssetManager;
import engine.core.Input;
import engine.render.Texture;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteRendererComponent;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GameState extends State {

    private static final float speed = 10.0f;

    @Override
    public void OnCreate() {
        super.OnCreate();

        Texture texture = AssetManager.LoadTexture(this, "image.png");
        Texture bravenatorHead = AssetManager.LoadTexture(this, "bravenator.png");

        GameObject gameObject = CreateGameObject();
        gameObject.transform.scale = new Vector2f(14.0f, 10.0f);
        gameObject.transform.position = new Vector2f(-10.0f, 0.0f);
        gameObject.AddComponent(new SpriteRendererComponent(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), texture));

        GameObject bravenatorHeadObj = CreateGameObject();
        bravenatorHeadObj.transform.scale = new Vector2f(10.0f, 10.0f);
        bravenatorHeadObj.transform.position = new Vector2f(10.0f, 0.0f);
        bravenatorHeadObj.AddComponent(new SpriteRendererComponent(new Vector4f(1.0f), bravenatorHead));
    }

    @Override
    public void OnUpdate(float deltaTime) {
        super.OnUpdate(deltaTime);

        if(Input.IsKey(Input.KEY_RIGHT)) {
            super.camera.SetPosition(super.camera.GetPosition().add(deltaTime * speed, 0.0f));
        }

        if(Input.IsKey(Input.KEY_LEFT)) {
            super.camera.SetPosition(super.camera.GetPosition().add(-deltaTime * speed, 0.0f));
        }

        if(Input.IsKey(Input.KEY_UP)) {
            super.camera.SetPosition(super.camera.GetPosition().add(0.0f, deltaTime * speed));
        }

        if(Input.IsKey(Input.KEY_DOWN)) {
            super.camera.SetPosition(super.camera.GetPosition().add(0.0f, -deltaTime * speed));
        }

//        super.camera.SetPosition(super.camera.GetPosition().add(deltaTime * 5,  deltaTime * 5));
    }

}
