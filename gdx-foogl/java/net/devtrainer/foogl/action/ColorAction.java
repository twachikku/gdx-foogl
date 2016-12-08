package net.devtrainer.foogl.action;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

/**
 * Action for setting color of actor
 * @author Wachirawut Thamviset
 */
public class ColorAction extends TemporalAction {

    private float startR, startG, startB, startA;
    private Color color;
    private final Color end = new Color();

    public ColorAction(Color color, float duration, Interpolation tween) {
        super();
        this.end.set(color);
        this.setDuration(duration);
        this.setInterpolation(tween);
    }

    protected void begin() {
        if (color == null && actor != null) {
            color = actor.getColor();
            startR = color.r;
            startG = color.g;
            startB = color.b;
            startA = color.a;
        }
    }

    protected void update(float percent) {
        float r = startR + (end.r - startR) * percent;
        float g = startG + (end.g - startG) * percent;
        float b = startB + (end.b - startB) * percent;
        float a = startA + (end.a - startA) * percent;
        color.set(r, g, b, a);
        actor.setColor(color);
    }

    public Color getColor() {
        return color;
    }

    /**
     * Sets the color to modify. If null (the default), the
     * {@link #getActor() actor's} {@link Actor#getColor() color} will be used.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getEndColor() {
        return end;
    }

    /**
     * Sets the color to transition to. Required.
     */
    public void setEndColor(Color color) {
        end.set(color);
    }
}
