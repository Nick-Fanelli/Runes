package game;

import engine.core.Application;
import engine.utils.Logger;
import game.states.GameState;

public final class Runes {

    public static void main(String[] args) {
        Logger.SetLoggerLevel(Logger.LoggerLevel.INFO);

        Application application = new Application("Runes");
        application.StartApplication(new GameState());
    }

}
