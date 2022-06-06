package engine.state.component;

import engine.state.GameObject;

public abstract class Component {

    protected GameObject parentObject = null;

    public void OnCreate() {}
    public void OnUpdate(float deltaTime) {}
    public void OnRender() {}
    public void OnDestroy() {}

    public void SetParentGameObject(GameObject gameObject) {
        this.parentObject = gameObject;
    }

}
