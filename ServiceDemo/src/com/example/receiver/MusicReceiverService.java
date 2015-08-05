package com.example.receiver;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.example.R;
import com.example.Utils;


public class MusicReceiverService extends Service {

	public static final String TAG = "MusicReceiverService";

	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "show media player", Toast.LENGTH_SHORT).show();

		String p = Utils.getCurProcessName(this);		
		Thread thread = Thread.currentThread();
		
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.xby);
			mediaPlayer.setLooping(false);
		}
	}

	@SuppressLint("ShowToast")
	@Override
	public void onDestroy() {
		Toast.makeText(this, "stop media player", Toast.LENGTH_SHORT);
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int op = bundle.getInt("op");
				switch (op) {
				case 1:
					play();
					break;
				case 2:
					stop();
					break;
				case 3:
					pause();
					break;
				}
			}
		}
	}

	public void play() {
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
