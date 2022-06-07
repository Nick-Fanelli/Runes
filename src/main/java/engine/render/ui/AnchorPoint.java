package engine.render.ui;

import org.joml.Vector2f;


public enum AnchorPoint {
    TOP(0, 1),
    RIGHT(1, 0),
    BOTTOM(0, -1),
    LEFT(-1, 0),
    TOP_LEFT(-1, 1),
    TOP_RIGHT(1, 1),
    BOTTOM_RIGHT(1, -1),
    BOTTOM_LEFT(-1, -1),
    CENTER(0, 0);

    public final Vector2f[] offsets;

    AnchorPoint(float xOffset, float yOffset) {
        this.offsets = new Vector2f[4];

        Vector2f[] quadVertexPositions = {
                new Vector2f(-0.5f, -0.5f),
                new Vector2f(0.5f, -0.5f),
                new Vector2f(0.5f, 0.5f),
                new Vector2f(-0.5f, 0.5f)
        };

        for(int i = 0; i < this.offsets.length; i++) {
            this.offsets[i] = new Vector2f(
                    quadVertexPositions[i].x - xOffset / 2.0f,
                    quadVertexPositions[i].y - yOffset / 2.0f);
        }

    }
}
