package engine.render.ui;

import engine.state.State;

import java.util.ArrayList;

public class UILayer {

    private final UIRenderer uiRenderer;

    protected ArrayList<UIElement> uiElements = new ArrayList<>();

    public UILayer(State state) {
        this.uiRenderer = state.GetRenderer().GetUIRenderer();
    }

    public void OnCreate() {

    }

    public void OnUpdate() {
        for(UIElement element : uiElements)
            element.OnUpdate();
    }

    public void OnRender() {
        for(UIElement element : uiElements)
            element.OnRender(uiRenderer);
    }

    public void OnDestroy() {

    }

    public void AddUIElement(UIElement uiElement) {
        this.uiElements.add(uiElement);
    }

    public void RemoveUIElement(UIElement uiElement) {
        this.uiElements.remove(uiElement);
    }

    public UIRenderer GetUIRenderer() { return this.uiRenderer; }

}
