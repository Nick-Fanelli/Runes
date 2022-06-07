package engine.render.ui;

import engine.state.Transform;

public abstract class UIElement {

    private boolean isChanged = false;

    public Transform transform = new Transform();
    public AnchorPoint anchorPoint = AnchorPoint.TOP_LEFT;

    public void OnUpdate() {}
    public void OnRender(UIRenderer uiRenderer) {}

}
