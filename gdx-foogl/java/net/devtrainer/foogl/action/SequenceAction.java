package net.devtrainer.foogl.action;

import net.devtrainer.foogl.actor.Actor;

public class SequenceAction extends ParallelAction {
    int index=0;
    public SequenceAction() {
    }

    public SequenceAction(Action action1) {
        super(action1);
    }

    public boolean act(float delta) {
        if (actions.size <= index ) {
            return true;
        }
        Action a = actions.get(index);        
        if (a.actor == null) {
            a.actor = this.actor;
        }
        if (a.act(delta)) {
            a.restart();
            if(isAutoRemove()){
              actions.removeIndex(index);
            }else{
              index++;
            }
        }
        return false;
    }

    @Override
    public void restart() {
        super.restart();
        index=0;        
    }    
}
