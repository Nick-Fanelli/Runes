package game;

import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteRendererComponent;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GameState extends State {

    @Override
    public void OnCreate() {
        super.OnCreate();

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
        super.camera.SetPosition(super.camera.GetPosition().add(deltaTime * 5,  deltaTime * 5));
    }

}
