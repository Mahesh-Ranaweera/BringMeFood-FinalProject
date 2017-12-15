package com.travmahrajvar.bringmefood.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.travmahrajvar.bringmefood.R;

import java.util.ArrayList;

/**
 * Created by Travis on 11/12/17.
 */

public class WanterAdapter extends BaseAdapter {
	
	private Context context;
	ArrayList<Wanter> wanters;
	
	public WanterAdapter(@NonNull Context context, @NonNull ArrayList<Wanter> objects) {
		this.context = context;
		wanters = objects;
	}
	
	@Override
	public int getCount() {
		return wanters.size();
	}
	
	@Override
	public Object getItem(int i) {
		return wanters.get(i);
	}
	
	@Override
	public long getItemId(int i) {
		return i;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Wanter w = wanters.get(position);
		
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.wanter_adapter_view, parent, false);
		
		TextView nameBox = convertView.findViewById(R.id.txtWanterAdapter_name);
		TextView orderListBox = convertView.findViewById(R.id.txtWanterAdapter_orderListSize);
		Button removeBtn = convertView.findViewById(R.id.approvedUsers_remove);
		
		nameBox.setText(w.getName());
		
		String wants = "";
		if(w.getOrderList() != null) {
			for (String item : w.getOrderList()) {
				wants += item + "; ";
			}
		}
		
		//orderListBox.setText(convertView.getResources().getQuantityString(R.plurals.wanterAdapter_orderSize, w.getOrderList().size(), w.getOrderList().size()));
		orderListBox.setText(wants);
		
		final int pos = position;
		removeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				removeWanter(pos);
				//TODO Move user to the pending list? Or just remove them entirely?
			}
		});
		
		return convertView;
	}


	public Wanter removeWanter(int position){
		Wanter w = wanters.remove(position);
		notifyDataSetChanged();
		return w;
	}
	
	public void addWanter(Wanter w){
		wanters.add(w);
		notifyDataSetChanged();
	}
}
