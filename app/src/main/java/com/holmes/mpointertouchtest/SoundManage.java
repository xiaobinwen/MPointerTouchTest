package com.holmes.mpointertouchtest;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

/**
 * Created by holmes on 18-2-8.
 */

class SoundManager {

	// SoundPool is used for short sound effects
	private SoundPool mSoundPool;

	// An array of sound clip ids
	public ArrayList<Integer> mSoundIds;


	/** Initialize the sound pool and load our sound clips. */
	public SoundManager(Context context)
	{
		mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mSoundIds = new ArrayList<Integer>();

		mSoundIds.add(mSoundPool.load(context, R.raw.domino_tap, 1));
	}


	/** Play a sound with the given index and volume. */
	public void playSound(int soundIndex, float volume)
	{
		if (soundIndex < 0 || soundIndex >= mSoundIds.size()) {
			return;
		}

		mSoundPool.play(mSoundIds.get(soundIndex), volume, volume, 1, 0, 1.0f);
	}
}
