package game.ui;

import engine.asset.AssetManager;
import engine.render.ui.AnchorPoint;
import engine.render.ui.UIImage;
import engine.render.ui.UILayer;
import engine.render.ui.UIPanel;
import engine.state.State;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class GameUILayer extends UILayer {

    public GameUILayer(State state) {
        super(state);
    }

    @Override
    public void OnCreate() {

//        // Panel
//        UIPanel panel = new UIPanel();
//        panel.transform.position = new Vector2f(-0.95f, 0.9f);
//        panel.transform.scale = new Vector2f(0.5f, 1.0f);
//        panel.backgroundColor = new Vector4f(1.0f, 1.0f, 1.0f, 0.95f);
//        AddUIElement(panel);
//
//        // Image
//        UIImage image = new UIImage();
//        image.anchorPoint = AnchorPoint.TOP_RIGHT;
//        image.transform.position = new Vector2f(1.0f, 1.0f);
//        image.texture = AssetManager.LoadTexture(state, "bravenator.png");
//        AddUIElement(image);
    }
}
