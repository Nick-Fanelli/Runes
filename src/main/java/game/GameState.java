package game;

import engine.asset.AssetManager;
import engine.render.Texture;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteRendererComponent;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.io.File;
import java.net.URL;

public class GameState extends State {

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

//        for(int x = 0; x < 100; x++) {
//            for(int y = 0; y < 100; y++) {
//                GameObject gameObject = CreateGameObject();
//                gameObject.transform.position = new Vector2f(x, y);
//                gameObject.transform.scale = new Vector2f(0.9f, 0.9f);
//                gameObject.AddComponent(new SpriteRendererComponent(new Vector4f(x / 100.0f, y / 100.0f, 1.0f, 1.0f)));
//            }
//        }


    }

    @Override
    public void OnUpdate(float deltaTime) {
        super.OnUpdate(deltaTime);
//        super.camera.SetPosition(super.camera.GetPosition().add(deltaTime * 5,  deltaTime * 5));
    }

}
