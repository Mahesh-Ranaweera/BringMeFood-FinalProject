package com.travmahrajvar.bringmefood;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

public class GettingFoodActivity extends AppCompatActivity
		implements GettingFoodFragment_SessionInfo.OnFragmentInteractionListener,
		GettingFoodFragment_PendingUsers.OnFragmentInteractionListener,
		GettingFoodFragment_ApprovedUsers.OnFragmentInteractionListener {
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;
	
	private String curSessionKey;
	private String curSessionRestaurant;
	private String curSessionLocation;
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getting_food);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		curSessionKey = getIntent().getStringExtra(getString(R.string.curSessionKey_identifier));
		curSessionRestaurant = getIntent().getStringExtra(getString(R.string.curSessionRestaurant_identifier));
		curSessionLocation = getIntent().getStringExtra(getString(R.string.curSessionLocation_identifier));
		
	}
	
	public void displayMenu(View view) {
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenuInflater().inflate(R.menu.main_menu_nav, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				if(menuItem.getTitle().toString().equals(getString(R.string.menu_signOut))){
					//Sign out user
					FirebaseHandler.signOutCurrentUser();
					finish();
				}
				return true;
			}
		});
		
		popupMenu.show();
	}
	
	@Override
	public void onFragmentInteraction(Uri uri) {
		//Don't really know why this is needed, but the activity crashes without it, so... Here it is.
		Log.i("GettingFoodActivity", "OnFragmentInteraction fired: " + uri.toString());
	}
	
	/**
	 * Copies the current session code to the clipboard.
	 * @param view Should be the copy button from the SessionInfo fragment.
	 */
	public void copyKeyToClipboard(View view) {
		ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("Session code", curSessionKey);
		clipboard.setPrimaryClip(clip);
		Toast.makeText(this, getString(R.string.clipboard_copied), Toast.LENGTH_SHORT).show();
	}
	/**
	 *
	 *
	 * Sends the current session key as a plain text message to all available sharing providers.
	 * @param view Should be the share button from the SessionInfo fragment.
	 */
	public void shareKey(View view) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sendTo_message, curSessionRestaurant, curSessionKey));
		shareIntent.setType("text/plain");
		startActivity(Intent.createChooser(shareIntent, getString(R.string.sendTo_title)));
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 *
	 * (Created from the default New Tabbed Activity section in Android Studio.)
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			//Set each tab as appropriate
			switch(position){
				case 1: // Approved users
					return GettingFoodFragment_ApprovedUsers.newInstance("", "");
				case 2: // Pending users
					return GettingFoodFragment_PendingUsers.newInstance("", "");
				case 0: // Session info
				default:
					return GettingFoodFragment_SessionInfo.newInstance(curSessionKey, curSessionRestaurant, curSessionLocation);
			}
		}
		
		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}
	}
}
