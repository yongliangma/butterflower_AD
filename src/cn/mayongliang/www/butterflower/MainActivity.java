package cn.mayongliang.www.butterflower;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import cn.mayongliang.www.butterflower.adapter.ListViewAdapter;
import cn.mayongliang.www.butterflower.bean.PoemBean;
import cn.mayongliang.www.butterflower.bean.PoemPool;
import cn.mayongliang.www.butterflower.service.MusicService;
import cn.mayongliang.www.butterflower.util.XmlHandler;

public class MainActivity extends Activity {
	private List<PoemBean> mPoemBeanData;
	private ListView mListView;
	private ListViewAdapter mListViewAdapter;
	private Button mExitButton, mMusicButton;
	private Intent mIntent;

	public static final int MUSIC_STATE_REQUEST_CODE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mIntent = new Intent(MainActivity.this, MusicService.class);
		mListView = (ListView) findViewById(R.id.lv_all_poem_lists);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				PoemPool.mPoemList = mPoemBeanData;
				PoemBean poemBean = mPoemBeanData.get(position);
				Intent intent = new Intent(MainActivity.this,
						DetailsActivity.class);
				intent.putExtra("poembean", poemBean);
				intent.putExtra("position", position);
				intent.putExtra("musicstate", mMusicButton.getText().toString());
				startActivityForResult(intent, MUSIC_STATE_REQUEST_CODE);
			}
		});
		mExitButton = (Button) findViewById(R.id.btn_exit);
		mExitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				exit();
			}
		});

		mMusicButton = (Button) findViewById(R.id.btn_music);
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

		initPoemData();
		AdManager.init(MainActivity.this, "bfd1fad449b77d61",
				"da3550ed5cb1ecdf", 30, false);
		LinearLayout adViewLayout = (LinearLayout) findViewById(R.id.adViewLayout);
		adViewLayout.addView(new AdView(this), new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		mListViewAdapter = new ListViewAdapter(MainActivity.this, mPoemBeanData);
		mListView.setAdapter(mListViewAdapter);
	}

	private void initPoemData() {
		mPoemBeanData = new ArrayList<PoemBean>();
		try {
			InputStream in = getResources().getAssets().open("poems.xml");
			parseXml(in, mPoemBeanData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// PoemBean poemBean = new PoemBean(
		// "蝶恋花·花褪残红青杏小",
		// "花褪残红青杏小。\n燕子飞时，\n绿水人家绕。\n枝上柳绵吹又少。\n天涯何处无芳草。\n\n墙里秋千墙外道。\n墙外行人，\n墙里佳人笑。\n笑渐不闻声渐悄。\n多情却被无情恼。",
		// "苏轼", "宋");
		// mPoemBeanData.add(poemBean);
		// poemBean = new PoemBean(
		// "蝶恋花·蝶懒莺慵春过半",
		// "蝶懒莺慵春过半。\n花落狂风，\n小院残红满。\n午醉未醒红日晚，\n黄昏帘幕无人卷。\n\n云鬓鬅松眉黛浅。\n总是愁媒，\n欲诉谁消遣。\n未信此情难系绊，\n杨花犹有东风管。",
		// "苏轼", "宋");
		// mPoemBeanData.add(poemBean);
	}

	private void parseXml(InputStream in, List<PoemBean> poembeandata) {
		try {
			SAXParser sp = SAXParserFactory.newInstance().newSAXParser();
			XMLReader xr = sp.getXMLReader();
			XmlHandler xmlHandler = new XmlHandler(poembeandata);
			xr.setContentHandler(xmlHandler);
			xr.parse(new InputSource(in));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			if (requestCode == MUSIC_STATE_REQUEST_CODE) {
				String musicStateString = data.getStringExtra("musicstate");
				mMusicButton.setText(musicStateString);
			}
			break;
		}
	}

	private void exit() {
		mPoemBeanData = null;
		stopService(mIntent);
		finish();
	}

}