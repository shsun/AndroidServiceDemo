package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.bind.PlayBindMusicActivity;
import com.example.receiver.PlayMusicRecevicerActivity;
import com.example.remote.PlayRemoteMusicActivity;
import com.example.service.PlayMusicServiceActivity;

public class MainActivity extends Activity implements OnClickListener {

	public static final String TAG = "MainActivity";

	private Button musicServiceBtn;
	private Button musicReceiverBtn;
	private Button bindMusicServiceBtn;
	private Button remoteMusicServiceBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.i(TAG, "onCreate");

		musicServiceBtn = (Button) findViewById(R.id.musicService);
		musicReceiverBtn = (Button) findViewById(R.id.musicReceiver);
		bindMusicServiceBtn = (Button) findViewById(R.id.bindMusicService);
		remoteMusicServiceBtn = (Button) findViewById(R.id.remoteMusicService);

		musicServiceBtn.setOnClickListener(this);
		musicReceiverBtn.setOnClickListener(this);
		bindMusicServiceBtn.setOnClickListener(this);
		remoteMusicServiceBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.musicService:
			startActivity(new Intent(this, PlayMusicServiceActivity.class));
			break;

		case R.id.musicReceiver:
			startActivity(new Intent(this, PlayMusicRecevicerActivity.class));
			break;

		case R.id.bindMusicService:
			startActivity(new Intent(this, PlayBindMusicActivity.class));
			break;

		case R.id.remoteMusicService:
			startActivity(new Intent(this, PlayRemoteMusicActivity.class));
			break;
		}
	}

	@Override
	public void onPause() {
		Log.i(TAG, "onPause");

		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i(TAG, "onResume");

		super.onResume();
	}

	@Override
	public void onRestart() {
		Log.i(TAG, "onRestart");

		super.onRestart();
	}

	@Override
	public void onStop() {
		Log.i(TAG, "onStop");

		super.onStop();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.i(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();

	}

}