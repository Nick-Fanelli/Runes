package engine.state;

import engine.core.display.WindowResizeEvent;

public class StateManager {

    private static State currentState = null;

    public static void SetCurrentState(State currentState) {
        if(StateManager.currentState != null) {
            State prevCurrentState = StateManager.currentState;
            StateManager.currentState = null;
            prevCurrentState.OnDestroy();
        }

        currentState.OnCreate();
        StateManager.currentState = currentState;
    }

    public void OnUpdate(float deltaTime) {
        if(StateManager.currentState != null)
            StateManager.currentState.OnUpdate(deltaTime);
    }

    public void OnDestroy() {
        if(StateManager.currentState != null)
            StateManager.currentState.OnDestroy();
    }

    public void OnWindowResize(WindowResizeEvent resizeEvent) {
        if(StateManager.currentState != null)
            StateManager.currentState.OnWindowResize(resizeEvent);
    }

}
