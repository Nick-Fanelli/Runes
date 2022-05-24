package engine.state;

import engine.state.component.Component;

import java.util.ArrayList;

public class GameObject {

    private final State parentState;
    private final ArrayList<Component> components = new ArrayList<>();

    public Transform transform;

    public GameObject(State state) {
        this.parentState = state;
        this.transform = new Transform();
    }

    public void OnCreate() {

    }

    public void OnUpdate(float deltaTime) {
        for(Component component : components)
            component.OnUpdate(deltaTime);
    }

    public void OnDestroy() {
        for(Component component : components)
            component.OnDestroy();
    }

    public void AddComponent(Component component) {
        component.SetParentGameObject(this);
        component.OnCreate();
        this.components.add(component);
    }

    public State GetParentState() { return this.parentState; }

}
