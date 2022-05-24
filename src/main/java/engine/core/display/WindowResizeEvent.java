package engine.core.display;

import engine.core.eventsystem.EventListener;

public class WindowResizeEvent extends EventListener.Event {

    public final float aspectRation;

    public WindowResizeEvent(float aspectRatio) {
        this.aspectRation = aspectRatio;
    }

}
