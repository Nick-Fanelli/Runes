package engine.core.eventsystem;

import java.util.ArrayList;

public class EventSystem<EventType extends EventListener.Event> {

    protected final ArrayList<EventListener<EventType>> eventListeners = new ArrayList<>();

    public void AddEventListener(EventListener<EventType> eventListener) {
        eventListeners.add(eventListener);
    }

    public void RemoveEventListener(EventListener<EventType> eventListener) {
        eventListeners.remove(eventListener);
    }

    public void CallEvent(EventType event) {
        for(EventListener<EventType> eventListener : eventListeners) {
            eventListener.OnEvent(event);
        }
    }

    public ArrayList<EventListener<EventType>> GetAllEventListeners() { return this.eventListeners; }

}
