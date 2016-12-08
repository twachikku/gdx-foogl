package net.devtrainer.foogl.action;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

/**
 *
 */
public class AlphaAction extends TemporalAction {

    float start = -1, alpha = 0;

    public AlphaAction(float startAlpha, float alpha, float duration, Interpolation tween) {
        super(duration,tween);
        start=startAlpha;
        this.alpha = alpha;
    }

    public AlphaAction(float alpha, float duration) {
        this(-1, alpha, duration, null);
    }

    void setAlpha(float a) {
        alpha = a;
    }

    @Override
    protected void begin() {
        super.begin();
        if (start == -1) {
            start = actor.getAlpha();
        }
    }

    @Override
    protected void update(float percent) {
      float a = start + (alpha - start) * percent;
      actor.setAlpha(a);
    }
}
