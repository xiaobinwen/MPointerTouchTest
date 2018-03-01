package com.example.holmes.mpointertouchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	private SoundManager soundManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		soundManager = new SoundManager(this);
//		soundManager.playSound(0, 1.0f  );
	}
}
