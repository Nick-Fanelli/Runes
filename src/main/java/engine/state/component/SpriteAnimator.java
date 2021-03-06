package engine.state.component;

import engine.render.sprite.SpriteAnimation;
import engine.render.sprite.SpriteSheet;

public class SpriteAnimator extends Component {

    private final SpriteSheet spriteSheet;
    private final Sprite rendererComponent;

    private SpriteSheet.Sprite currentSprite = null;

    private boolean isRunning = false;

    private SpriteAnimation currentAnimation;

    private int currentFrame = 0;
    private float frameTimer = 0.0f;

    public SpriteAnimator(SpriteSheet spriteSheet, Sprite rendererComponent) {
        this.spriteSheet = spriteSheet;
        this.rendererComponent = rendererComponent;
        this.currentAnimation = new SpriteAnimation(0, 0, 1, false);
        UpdateSprite();
    }

    @Override
    public void OnUpdate(float deltaTime) {
        if(!isRunning)
            return;

        frameTimer += deltaTime * 1000;

        if(frameTimer >= currentAnimation.frameDuration) {
           currentFrame++;

           if(currentFrame >= currentAnimation.frameCount) {
               currentFrame = 0;
           }

           frameTimer = 0;

           UpdateSprite();
        }
    }

    public void PlayIfNot(SpriteAnimation animation) {
        if(this.currentAnimation.equals(animation) && isRunning)
            return;

        PlayAnimation(animation);
    }

    public void PlayAnimation(SpriteAnimation animation) {
        this.frameTimer = animation.frameDuration;
        this.currentFrame = 0;

        this.currentAnimation = animation;
        this.isRunning = true;
    }

    public void FreezeAnimation() {
        this.isRunning = false;
    }

    public void GoToFrame(int frame) {
        currentFrame = frame;
        UpdateSprite();
    }

    private void UpdateSprite() {
        if(!currentAnimation.isFlipped)
            this.currentSprite = spriteSheet.GetSprite(currentFrame, currentAnimation.animationRow);
        else
            this.currentSprite = spriteSheet.GetSpriteFlipped(currentFrame, currentAnimation.animationRow);

        this.rendererComponent.sprite = currentSprite;
    }

    public SpriteSheet.Sprite GetCurrentSprite() { return currentSprite; }

 }
