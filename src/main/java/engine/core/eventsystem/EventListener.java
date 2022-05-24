package engine.core.eventsystem;

public interface EventListener<EventType extends EventListener.Event> {

    abstract class Event {}

    void OnEvent(EventType event);

}
