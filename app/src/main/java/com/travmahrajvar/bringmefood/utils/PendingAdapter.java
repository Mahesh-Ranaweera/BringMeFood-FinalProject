package com.travmahrajvar.bringmefood.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Travis on 11/12/17.
 */

public class PendingAdapter extends BaseAdapter {
	
	private Context context;
	ArrayList<Wanter> wanters;
	final String currentSession;
	public DatabaseReference mRefWanters;
	public DatabaseReference mRefApproves;
	
	public PendingAdapter(@NonNull Context context, @NonNull ArrayList<Wanter> objects, String currentSession) {
		this.context = context;
		this.currentSession = currentSession;
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
		final Wanter w = wanters.get(position);
		
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.pending_adapter_view, parent, false);
		
		TextView nameBox = convertView.findViewById(R.id.txtWanterAdapter_name);
		TextView orderListBox = convertView.findViewById(R.id.txtWanterAdapter_orderListSize);
		ImageButton acceptButton = convertView.findViewById(R.id.pendingUsers_accept);
		ImageButton denyButton = convertView.findViewById(R.id.pendingUsers_deny);
		
		nameBox.setText(w.getName());
		
		String wants = "";
		if(w.getOrderList() != null) {
			for (String item : w.getOrderList()) {
				wants += item + "; ";
			}
		}

		orderListBox.setText(wants);
		
		final int pos = position;
		final String uid = w.getUid();
		acceptButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				removeWanter(pos);
				//TODO add to approved list

				getDBData(uid);
				getCurrentPending(uid);

			}
		});
		
		denyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				removeWanter(pos);
				//TODO send notification to wanter: Denied

				deleteData(uid);
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


	public void getDBData(String uid) {
		mRefWanters = FirebaseDatabase.getInstance().getReference().child("getting").child(currentSession).child("wanterlist");

		if (mRefWanters != null) {

			mRefWanters.orderByValue().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for(DataSnapshot snapshot : dataSnapshot.getChildren()){
						snapshot.getRef().setValue(null);
					}
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});
		}
	}

	public void deleteData(String uid) {
		mRefWanters = FirebaseDatabase.getInstance().getReference().child("getting").child(currentSession).child("wanterlist");

		if (mRefWanters != null) {

			mRefWanters.orderByValue().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for(DataSnapshot snapshot : dataSnapshot.getChildren()){
						snapshot.getRef().setValue(null);
					}
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});
		}
	}

	public void getCurrentPending(final String uid) {
		mRefApproves = FirebaseDatabase.getInstance().getReference().child("getting").child(currentSession).child("approved");

		if (mRefApproves != null) {

			mRefApproves.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					ArrayList<String> approvedList = new ArrayList<>();
					if (dataSnapshot.getValue() != null) {

						for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
							Log.i("values", "val" + snapshot);
							approvedList.add(snapshot.getValue().toString());
						}

						if(!checkID(approvedList, uid)){
							approvedList.add(uid);
							//Upload updated approved array
							FirebaseHandler.updateApprovedList(approvedList, currentSession);
						}
					}else{
						approvedList.add(uid);
						//Upload updated approved array
						FirebaseHandler.updateApprovedList(approvedList, currentSession);
					}
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});
		}
	}

	public boolean checkID(ArrayList<String> listArr, String uid){

		List<String> list = listArr;

		if(list.contains(uid)){
			return true;
		}
		return  false;
	}
}
