package com.example.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.R;
import com.example.Utils;

public class PlayMusicServiceActivity extends Activity implements OnClickListener {

	public static final String TAG = "PlayMusicServiceActivity";

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

		String p = Utils.getCurProcessName(this);		
		Thread thread = Thread.currentThread();

		
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
		int operation = -1;
		intent = new Intent("com.example.service.musicService");
		switch (v.getId()) {
		case R.id.play:
			operation = 1;
			break;
		case R.id.stop:
			operation = 2;
			break;
		case R.id.pause:
			operation = 3;
			break;
		case R.id.close:
			this.finish();
			break;
		case R.id.exit:
			operation = 4;
			stopService(intent);
			this.finish();
			break;
		}

		Bundle bundle = new Bundle();
		bundle.putInt("op", operation);
		intent.putExtras(bundle);

		startService(intent);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();

		if (intent != null) {
			stopService(intent);
		}
	}
}