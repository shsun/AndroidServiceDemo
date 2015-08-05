package com.example.remote;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.remote.IMusicControlService;
import com.example.remote.IMusicControlService.Stub;
import com.example.R;
import com.example.Utils;

public class RemoteMusicService extends Service {

	public static final String TAG = "RemoteMusicService";

	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent intent) {
		
		String p = Utils.getCurProcessName(this);		
		Thread thread = Thread.currentThread();
		
		return binder;
	}

	private final IMusicControlService.Stub binder = new IMusicControlService.Stub() {

		@Override
		public void play() throws RemoteException {
			if (mediaPlayer == null) {
				mediaPlayer = MediaPlayer.create(RemoteMusicService.this, R.raw.xby);
				mediaPlayer.setLooping(false);
			}
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
		}

		@Override
		public void pause() throws RemoteException {
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}
		}

		@Override
		public void stop() throws RemoteException {
			if (mediaPlayer != null) {
				mediaPlayer.stop();
				try {
					mediaPlayer.prepare();
				} catch (IOException e) {
					Log.i(TAG, e.getMessage());
				}
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
}
