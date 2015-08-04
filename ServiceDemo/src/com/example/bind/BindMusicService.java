package com.example.bind;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.R;

public class BindMusicService extends Service {

	private MediaPlayer mediaPlayer;

	private final IBinder binder = new MusicBinder();

	public class MusicBinder extends Binder {
		BindMusicService getService() {
			return BindMusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Toast.makeText(this, "show media player", Toast.LENGTH_SHORT).show();
	}

	@SuppressLint("ShowToast")
	@Override
	public void onDestroy() {
		super.onDestroy();

		Toast.makeText(this, "stop media player", Toast.LENGTH_SHORT);
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	public void play() {
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.xby);
			mediaPlayer.setLooping(false);
		}
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	public void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			try {
				mediaPlayer.prepare();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
