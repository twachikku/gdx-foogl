
package net.devtrainer.foogl.action;

import com.badlogic.gdx.audio.Sound;

public class SoundAction extends Action {
	String name;
	Sound sound;
	float duration;
	float time = 0;

	public SoundAction (String soundname) {
		this(soundname, 0);
	}

	public SoundAction (String soundname, float duration) {
		this.name = soundname;
		this.duration = duration;
	}

	@Override
	public boolean act (float delta) {
		if (sound == null) {			
			sound = scene.builder.sound(name);
			if (sound != null) sound.play();
		}
		time += delta;
		if(time>duration){
			if (sound != null) sound.stop();
		}
		return time > duration;
	}
}
