package net.devtrainer.foogl.event;

import net.devtrainer.foogl.actor.Actor;

public interface IActorEvent {
   void handle(Object sender, Actor actor);
}
