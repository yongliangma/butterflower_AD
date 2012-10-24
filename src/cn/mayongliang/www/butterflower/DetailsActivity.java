package cn.mayongliang.www.butterflower;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.mayongliang.www.butterflower.bean.PoemBean;
import cn.mayongliang.www.butterflower.bean.PoemPool;
import cn.mayongliang.www.butterflower.service.MusicService;

public class DetailsActivity extends Activity {

	private TextView title;
	private TextView mainbody;
	private TextView author;
	private Button mBackButton;
	private Button mMusicButton;
	private RelativeLayout mDetailContentRelativeLayout;

	private PoemBean poemBean;
	private String musicStateString;
	private int mPosition = 0;
	private Intent mIntent;

	private GestureDetector mGestureDetector;

	public DetailsActivity() {
		mGestureDetector = new GestureDetector(mGestureListener);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_content);
		mIntent = new Intent(DetailsActivity.this, MusicService.class);
		poemBean = (PoemBean) getIntent().getSerializableExtra("poembean");
		mPosition = getIntent().getIntExtra("position", 0);
		musicStateString = (String) getIntent().getStringExtra("musicstate");

		mDetailContentRelativeLayout = (RelativeLayout) findViewById(R.id.rl_detail_content);
		mDetailContentRelativeLayout.setOnTouchListener(mTouchListener);
		mDetailContentRelativeLayout.setClickable(true);

		title = (TextView) findViewById(R.id.tv_detail_title);
		author = (TextView) findViewById(R.id.tv_detail_author);
		mainbody = (TextView) findViewById(R.id.tv_detail_mainbody);
		setDetails();
		mBackButton = (Button) findViewById(R.id.btn_back);
		mBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMusicState();
			}
		});
		mMusicButton = (Button) findViewById(R.id.btn_details_music);
		if (musicStateString.equals(getResources().getString(
				R.string.app_start_music))) {
			mMusicButton.setText(R.string.app_start_music);
		} else {
			mMusicButton.setText(R.string.app_stop_music);
		}
		mMusicButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mMusicButton
						.getText()
						.toString()
						.equals(getResources().getString(
								R.string.app_start_music))) {
					startService(mIntent);
					mMusicButton.setText(R.string.app_stop_music);
				} else {
					stopService(mIntent);
					mMusicButton.setText(R.string.app_start_music);
				}
			}
		});

		AdManager.init(DetailsActivity.this, "bfd1fad449b77d61",
				"da3550ed5cb1ecdf", 30, false);
		LinearLayout adViewLayout = (LinearLayout) findViewById(R.id.adViewLayout);
		adViewLayout.addView(new AdView(this), new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			sendMusicState();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void sendMusicState() {
		Intent i = new Intent();
		i.putExtra("musicstate", mMusicButton.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
	
	private void setDetails(){
		title.setText(poemBean.getTitle());
		author.setText(poemBean.getAuthor());
		mainbody.setText(poemBean.getMainbody());
	}

	public OnGestureListener mGestureListener = new OnGestureListener() {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			final int FLING_MIN_DISTANCE = 300;
			final int FLING_MIN_VELOCITY = 10;
			if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
					&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				// ，手势往左划，跳到下一个
				int position = mPosition + 1;
				if (position > PoemPool.mPoemList.size() - 1) {
					Toast.makeText(DetailsActivity.this, R.string.last_one,
							Toast.LENGTH_LONG).show();
				} else {
					poemBean = PoemPool.mPoemList.get(position);
					setDetails();
					mPosition++;
				}

			} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
					&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				int position = mPosition - 1;
				if (position < 0) {
					Toast.makeText(DetailsActivity.this, R.string.first_one,
							Toast.LENGTH_LONG).show();
				} else {
					poemBean = PoemPool.mPoemList.get(position);
					setDetails();
					mPosition--;

				}
			}
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
	};

	public OnTouchListener mTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return mGestureDetector.onTouchEvent(event);
		}
	};

}