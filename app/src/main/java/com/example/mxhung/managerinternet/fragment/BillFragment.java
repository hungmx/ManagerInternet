package com.example.mxhung.managerinternet.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mxhung.managerinternet.R;
import com.example.mxhung.managerinternet.adapter.BillAdapter;
import com.example.mxhung.managerinternet.adapter.ClientAdapter;
import com.example.mxhung.managerinternet.manager.DBManager;
import com.example.mxhung.managerinternet.model.Bill;
import com.example.mxhung.managerinternet.model.Client;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment {
	@Bind(R.id.tvEmpty)
	TextView tvEmpty;
	@Bind(R.id.lvBill)
	ListView lvBill;
	ArrayList<Bill> list;
	BillAdapter adapter;
	public BillFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bill, container, false);
		ButterKnife.bind(this, view);
		loadListBill();
		return view;
	}
	public void setAdapterListView(ArrayList<Bill> list){
		adapter = new BillAdapter(this,list);
		lvBill.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	public void loadListBill(){
		list = new ArrayList<Bill>();
		list.addAll(DBManager.INSTANCE.getListBill());
		if (list.size() == 0){
			tvEmpty.setVisibility(View.VISIBLE);
		}else {
			tvEmpty.setVisibility(View.GONE);
		}
		setAdapterListView(list);

	}

}
