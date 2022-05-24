package engine.state;

import engine.core.eventsystem.EventListener;

public class StateChangeEvent extends EventListener.Event {

    public final State previousState;
    public final State newState;

    public StateChangeEvent(State previousState, State newState) {
        this.previousState = previousState;
        this.newState = newState;
    }

}
