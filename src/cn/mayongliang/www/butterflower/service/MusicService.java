package cn.mayongliang.www.butterflower.service;

import cn.mayongliang.www.butterflower.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

	private MediaPlayer mPlayer;

	@Override
	public void onCreate() {
		super.onCreate();
		mPlayer = MediaPlayer.create(this, R.raw.dielianhua);
		mPlayer.setLooping(true);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		mPlayer.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mPlayer.stop();
		mPlayer.release();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
