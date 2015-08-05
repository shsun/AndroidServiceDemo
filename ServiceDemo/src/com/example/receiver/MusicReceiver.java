package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MusicReceiver extends BroadcastReceiver {

	public static final String TAG = "MusicReceiver";

	public MusicReceiver() {
		super();
	}

	@Override
	public void onReceive(Context pContext, Intent pIntent) {
		if (pIntent != null) {
			Bundle bundle = pIntent.getExtras();
			Intent itent = new Intent(pContext, MusicReceiverService.class);
			// call service for MusicReceiverService.class
			itent.putExtras(bundle);
			if (bundle != null) {
				int operationType = bundle.getInt("op");
				if (operationType == 4) {
					pContext.stopService(itent);
				} else {
					pContext.startService(itent);
				}
			}
		}
	}

}
