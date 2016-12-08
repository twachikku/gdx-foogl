package net.devtrainer.foogl.action;


import com.badlogic.gdx.math.Interpolation;

/**
 * Created by twachi on 4/12/2559.
 */

public class MoveByAction extends MoveToAction {
    float dx,dy;
    public MoveByAction(float dx,float dy, float duration,Interpolation interpolation){
        super(dx,dy,duration,interpolation);
        this.dx = dx;
        this.dy = dy;
        setDuration(duration);
    }
    protected void begin () {
        super.begin();
        setX(actor.getX()+dx);
        setY(actor.getY()+dy);
    }
}
