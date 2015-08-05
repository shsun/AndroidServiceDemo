package com.example.remote;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.remote.IMusicControlService;
import com.example.R;

public class PlayRemoteMusicActivity extends Activity implements OnClickListener {

	public static final String TAG = "PlayRemoteMusicActivity";

	
	private Button playBtn;
	private Button stopBtn;
	private Button pauseBtn;
	private Button exitBtn;

	private IMusicControlService musicService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_music_service);

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
		Intent intent = new Intent("com.example.remote.remoteMusicReceiver");
		bindService(intent, sc, Context.BIND_AUTO_CREATE); // bindService
	}

	@Override
	public void onClick(View v) {

		try {
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
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private ServiceConnection sc = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) { // connect
																				// Service
			musicService = IMusicControlService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// disconnect Service
			musicService = null;
		}

	};

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (sc != null) {
			unbindService(sc); // unBindService
		}
	}
}