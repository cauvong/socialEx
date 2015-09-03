package com.example.vynv.socialnetworking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vynv.socialnetworking.Instagram.RelationshipActivity;
import com.example.vynv.socialnetworking.R;
import com.example.vynv.socialnetworking.lazyload.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class RelationShipAdapter extends BaseAdapter {

	private ArrayList<HashMap<String, String>> usersInfo;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;

	public RelationShipAdapter(Context context,
							   ArrayList<HashMap<String, String>> usersInfo) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.usersInfo = usersInfo;
		this.imageLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflater.inflate(R.layout.relationship_inflater, null);
		ViewHolder holder = new ViewHolder();
		holder.ivPhoto = (ImageView) view.findViewById(R.id.ivImage);
		holder.tvFullName = (TextView) view.findViewById(R.id.tvFullName);
		holder.tvFullName.setText(usersInfo.get(position).get(
				RelationshipActivity.TAG_USERNAME));
		imageLoader.DisplayImage(
				usersInfo.get(position).get(RelationshipActivity.TAG_PROFILE_PICTURE),
				holder.ivPhoto);
		return view;
	}

	private class ViewHolder {
		private ImageView ivPhoto;
		private TextView tvFullName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return usersInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
