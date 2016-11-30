package net.devtrainer.foogl.plugin;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

import net.devtrainer.foogl.actor.Actor;

public interface TiledMapCollideCallBack {
   void handle(Actor p, TiledMapTile tile);
}
