package com.example.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.R;


public class PlayMusicServiceActivity extends Activity implements OnClickListener {

	private Button playBtn;
	private Button stopBtn;
	private Button pauseBtn;
	private Button exitBtn;
	
	private Button closeBtn;

	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_service);

		playBtn = (Button) findViewById(R.id.play);
		stopBtn = (Button) findViewById(R.id.stop);
		pauseBtn = (Button) findViewById(R.id.pause);
		exitBtn = (Button) findViewById(R.id.exit);
		closeBtn = (Button) findViewById(R.id.close);

		playBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
		closeBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int op = -1;
		intent = new Intent("com.example.service.musicService");

		switch (v.getId()) {
		case R.id.play:
			op = 1;
			break;
		case R.id.stop: // stop music
			op = 2;
			break;
		case R.id.pause: // pause music
			op = 3;
			break;
		case R.id.close: // close activity
			this.finish();
			break;
		case R.id.exit: // stopService
			op = 4;
			stopService(intent);
			this.finish();
			break;
		}

		Bundle bundle = new Bundle();
		bundle.putInt("op", op);
		intent.putExtras(bundle);

		startService(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (intent != null) {
			stopService(intent);
		}
	}
}