package engine.state;

import engine.core.display.WindowResizeEvent;
import engine.core.eventsystem.EventListener;
import engine.core.eventsystem.EventSystem;

public class StateManager {

    private static final EventSystem<StateChangeEvent> stateChangeEventSystem = new EventSystem<>();

    private static State currentState = null;

    public static void AddStateChangeEventListener(EventListener<StateChangeEvent> eventListener) {
        stateChangeEventSystem.AddEventListener(eventListener);
    }

    public static void SetCurrentState(State currentState) {
        State prevCurrentState = StateManager.currentState;

        if(StateManager.currentState != null) {
            StateManager.currentState = null;
            prevCurrentState.OnDestroy();
        }

        currentState.OnCreate();
        StateManager.currentState = currentState;

        stateChangeEventSystem.CallEvent(new StateChangeEvent(prevCurrentState, currentState));
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
