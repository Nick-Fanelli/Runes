package engine.render.sprite;

import engine.state.component.SpriteAnimatorComponent;

public class SpriteAnimation {

    public int animationRow;
    public int frameDuration;
    public int frameCount;
    public boolean shouldRepeat;
    public boolean isFlipped = false;

    public SpriteAnimation(int animationRow, int frameDuration, int frameCount, boolean shouldRepeat) {
        this.animationRow = animationRow;
        this.frameDuration = frameDuration;
        this.frameCount = frameCount;
        this.shouldRepeat = shouldRepeat;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SpriteAnimation other))
            return false;

        return  this.animationRow == other.animationRow &&
                this.frameDuration == other.frameDuration &&
                this.frameCount == other.frameCount &&
                this.shouldRepeat == other.shouldRepeat &&
                this.isFlipped == other.isFlipped;
    }
}
