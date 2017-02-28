package com.example.mxhung.managerinternet.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mxhung.managerinternet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


	public HelpFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_help, container, false);
		return view;
	}

}
