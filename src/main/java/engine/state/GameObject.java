package engine.state;

import engine.state.component.Component;

import java.util.ArrayList;

public class GameObject {

    protected final State parentState;
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

    public void OnRender() {
        for(Component component : components)
            component.OnRender();
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

    public <T extends Component> T GetComponent(Class<T> componentClass) {
        for(Component c : components) {
            if(componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch(ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Could not cast component!";
                }
            }
        }

        return null;
    }

    public <T extends Component> void RemoveComponent(Class<T> componentClass) {
        for(int i = 0; i < components.size(); i++) {
            Component c = components.get(i);

            if(componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public State GetParentState() { return this.parentState; }

}
