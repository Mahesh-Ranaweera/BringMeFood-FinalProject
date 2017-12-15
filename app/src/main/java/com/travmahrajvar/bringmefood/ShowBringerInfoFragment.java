package com.travmahrajvar.bringmefood;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.utils.NeedData;

/**
 * Created by 100553999 on 12/15/2017.
 */

public class ShowBringerInfoFragment extends ListFragment {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("getting").addValueEventListener(new ValueEventListener() {
            /**
             * The following method updates if any changes happen in this subset of data on the database
             * @param dataSnapshot
             */

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children){
                    NeedData needData = child.getValue(NeedData.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void finsihOff(){

    }
}
