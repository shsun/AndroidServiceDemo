package com.example.bind;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.R;

public class PlayBindMusicActivity extends Activity implements OnClickListener {

	public static final String TAG = "PlayBindMusicActivity";

	private Button playBtn;
	private Button stopBtn;
	private Button pauseBtn;
	private Button exitBtn;

	private BindMusicService musicService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bind_music_service);

		playBtn = (Button) findViewById(R.id.play);
		stopBtn = (Button) findViewById(R.id.stop);
		pauseBtn = (Button) findViewById(R.id.pause);
		exitBtn = (Button) findViewById(R.id.exit);

		playBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);

		connection();
	}

	private void connection() {
		Intent intent = new Intent("com.example.bind.bindService");
		bindService(intent, serviceConnectionListener, Context.BIND_AUTO_CREATE); 
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			musicService.play();
			break;
		case R.id.stop:
			if (musicService != null) {
				musicService.stop();
			}
			break;
		case R.id.pause:
			if (musicService != null) {
				musicService.pause();
			}
			break;
		case R.id.exit:
			this.finish();
			break;
		}
	}
	
	/**
	 * 
	 */
	private ServiceConnection serviceConnectionListener = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicService = ((BindMusicService.MusicBinder) (service)).getService();
			if (musicService != null) {
				musicService.play();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// disconnect Service
			musicService = null;
		}
	};

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");

		super.onDestroy();

		if (serviceConnectionListener != null) {
			unbindService(serviceConnectionListener);
		}
	}
}