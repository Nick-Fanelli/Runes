package engine.render.ui;

import engine.state.Transform;
import org.joml.Vector4f;

public class UIPanel extends UIElement {

    public Transform transform = new Transform();
    public Vector4f backgroundColor = new Vector4f(1.0f);

    public UIPanel() {}

    public UIPanel(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void OnRender(UIRenderer uiRenderer) {
        uiRenderer.DrawQuad(transform, anchorPoint, backgroundColor, null, null);
    }
}
