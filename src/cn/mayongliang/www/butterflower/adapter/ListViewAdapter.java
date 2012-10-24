package cn.mayongliang.www.butterflower.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.mayongliang.www.butterflower.R;
import cn.mayongliang.www.butterflower.bean.PoemBean;
import cn.mayongliang.www.butterflower.bean.PoemViewHolder;

public class ListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<PoemBean> mPoemBeanData;
	private Context mContext;

	public ListViewAdapter(Context context, List<PoemBean> poemBeanData) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mPoemBeanData = poemBeanData;
	}

	@Override
	public int getCount() {
		if (mPoemBeanData == null) {
			return 0;
		}
		return mPoemBeanData.size();
	}

	@Override
	public PoemBean getItem(int position) {
		if (mPoemBeanData == null) {
			return null;
		}
		return mPoemBeanData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PoemViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.poem_cell, null);
			holder = new PoemViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.author = (TextView) convertView.findViewById(R.id.tv_author);
			holder.dynasty = (TextView) convertView
					.findViewById(R.id.tv_dynasty);
			convertView.setTag(holder);
		} else {
			holder = (PoemViewHolder) convertView.getTag();
		}

		PoemBean poemBean = mPoemBeanData.get(position);
		holder.title.setText(poemBean.getTitle());
		holder.author.setText(poemBean.getAuthor());
		holder.dynasty.setText(poemBean.getDynasty());
		return convertView;
	}

}
