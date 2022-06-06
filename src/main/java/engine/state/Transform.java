package engine.state;

import org.joml.Vector2f;

public class Transform {

    public Vector2f position;
    public Vector2f scale;
    public float rotation = 0.0f;

    public Transform() {
        this(new Vector2f(0.0f, 0.0f));
    }

    public Transform(Vector2f position) {
        this(position, new Vector2f(1.0f, 1.0f));
    }

    public Transform(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform(Vector2f position, Vector2f scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

}
