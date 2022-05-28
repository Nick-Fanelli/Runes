package game.states;

import engine.state.State;
import game.objects.Player;

public class GameState extends State {

    private static final float speed = 10.0f;

    private Player player;

    @Override
    public void OnCreate() {
        super.OnCreate();

        player = new Player(this);
    }

    @Override
    public void OnUpdate(float deltaTime) {
        super.OnUpdate(deltaTime);
    }

}
