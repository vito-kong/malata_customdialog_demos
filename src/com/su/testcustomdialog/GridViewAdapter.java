package com.su.testcustomdialog;


import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

	private List<String> appInfos;
	private Context mContext;
	public TextView appinfo_tx, no_running_tx;
	public ImageView appinfo_img, appinfo_remove;
	private Handler mhandler;

	public GridViewAdapter(Context mContext, List<String> appinfos, Handler handler) {
		super();
		Log.i("TEST", "GridViewAdapter constructor ");
		this.appInfos = appinfos;
		this.mContext = mContext;
		this.mhandler = handler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("TEST", "getCount = " + appInfos.size());
	
		if (appInfos.size() == 0) {

		}

		return appInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		Log.i("TEST", "GridViewAdapter getView ");
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			
			holder.appinfo_img = (ImageView) convertView.findViewById(R.id.appinfo_img_recycler);
			holder.no_running_tx = (TextView) convertView.findViewById(R.id.appinfo_tx_recycler);
			holder.appinfo_remove = (ImageView) convertView.findViewById(R.id.appinfo_remove);
			holder.appinfo_remove.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_clear_all_pressed));
			convertView.setTag(holder);
			// appinfo_remove_img = (ImageView)
			// convertView.findViewById(R.id.appinfo_remove_img);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final View mconvertView = convertView;
	
		holder.no_running_tx.setText(appInfos.get(position).toString());

		holder.appinfo_remove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("ss", "remove_img");
				removeListItem(mconvertView, position);

			}
		});
		holder.appinfo_img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			
			}
		});

		return convertView;
	}
	private void removeListItem(View rowView, final int position) { 
        
        final Animation animation = (Animation) AnimationUtils.loadAnimation(rowView.getContext(), R.anim.floating_main_view_out); 
        animation.setAnimationListener(new AnimationListener() { 
            public void onAnimationStart(Animation animation) {} 
 
            public void onAnimationRepeat(Animation animation) {} 
 
            public void onAnimationEnd(Animation animation) { 
            	Message msg = new Message();
            	msg.what = 2;
				msg.arg1 = position;
				mhandler.sendMessage(msg);// make the item dismiss.
                //animation.cancel(); 
            } 
        }); 
        rowView.startAnimation(animation); 
    } 

	static class ViewHolder {
		 TextView no_running_tx;
		 ImageView appinfo_img;
		 ImageView appinfo_remove;
	}

}
