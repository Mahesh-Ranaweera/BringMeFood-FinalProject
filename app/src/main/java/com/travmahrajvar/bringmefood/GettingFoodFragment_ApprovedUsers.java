package com.travmahrajvar.bringmefood;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.travmahrajvar.bringmefood.utils.Wanter;
import com.travmahrajvar.bringmefood.utils.WanterAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GettingFoodFragment_ApprovedUsers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GettingFoodFragment_ApprovedUsers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GettingFoodFragment_ApprovedUsers extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	private OnFragmentInteractionListener mListener;
	
	public GettingFoodFragment_ApprovedUsers() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment GettingFoodFragment_ApprovedUsers.
	 */
	// TODO: Rename and change types and number of parameters
	public static GettingFoodFragment_ApprovedUsers newInstance(String param1, String param2) {
		GettingFoodFragment_ApprovedUsers fragment = new GettingFoodFragment_ApprovedUsers();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_getting_food_approvedusers, container, false);
		ListView approvedList = root.findViewById(R.id.listApprovedUsers);
		
		//TODO get make an actual list of users from Firebase
		ArrayList<Wanter> wanters = new ArrayList<Wanter>();
		
		ArrayList<String> w1List = new ArrayList<>();
		w1List.add("Aaa");
		w1List.add("Bbb");
		
		ArrayList<String> w2List = new ArrayList<>();
		w2List.add("Ccc");
		
		wanters.add(new Wanter("Joe", w1List));
		wanters.add(new Wanter("Jen", w2List));
		
		approvedList.setAdapter(new WanterAdapter(container.getContext(), wanters));
		
		return root;
	}
	
	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	
	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}
}
