package com.example.mxhung.managerinternet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mxhung.managerinternet.R;
import com.example.mxhung.managerinternet.fragment.BillFragment;
import com.example.mxhung.managerinternet.model.Bill;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MXHung on 7/4/2016.
 */
public class BillAdapter extends BaseAdapter{
	ArrayList<Bill> list;
	BillFragment fragment;
	Bill bill;
	public BillAdapter(BillFragment fragment,ArrayList<Bill> list){
		this.fragment = fragment;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list != null ? list.size() : null;
	}

	@Override
	public Bill getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		BillHolder holder;
		if (view ==  null){
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
			holder = new BillHolder(view);
			view.setTag(holder);
		}else {
			holder = (BillHolder)view.getTag();
		}
		holder.bind(getItem(position));
		bill = list.get(position);
		return view;
	}

	public class BillHolder{
		@Bind(R.id.tvBId) TextView tvBId;
		@Bind(R.id.tvName) TextView tvName;
		@Bind(R.id.tvMoney) TextView tvMoney;
		@Bind(R.id.tvPaymentDate) TextView tvPaymentDate;
		public BillHolder(View view){
			ButterKnife.bind(this, view);
		}

		public void bind(Bill bill){
			tvBId.setText("KH 00" + String.valueOf(bill.getbId()));
			tvName.setText(bill.getName());
			tvMoney.setText(bill.getMoney() + " VNĐ");
			tvPaymentDate.setText(bill.getPaymentDate());
		}
	}
}
