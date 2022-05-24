package engine.state.component;

import engine.state.GameObject;

public abstract class Component {

    protected GameObject parentObject = null;

    public abstract void OnCreate();
    public abstract void OnUpdate(float deltaTime);
    public abstract void OnDestroy();

    public void SetParentGameObject(GameObject gameObject) {
        this.parentObject = gameObject;
    }

}
