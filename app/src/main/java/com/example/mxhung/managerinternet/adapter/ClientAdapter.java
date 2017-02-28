package com.example.mxhung.managerinternet.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mxhung.managerinternet.R;
import com.example.mxhung.managerinternet.activity.EditActivity;
import com.example.mxhung.managerinternet.fragment.ListFragment;
import com.example.mxhung.managerinternet.model.Client;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MXHung on 6/16/2016.
 */
public class ClientAdapter extends BaseAdapter{
	ArrayList<Client> list;
	ListFragment fragment;
	Client client;
	public ClientAdapter(ListFragment fragment,ArrayList<Client> list){
		this.fragment = fragment;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list != null ? list.size() : null;
	}

	@Override
	public Client getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ClientHolder holder;
		if (view == null){
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client,parent,false);
			holder = new ClientHolder(view);
			view.setTag(holder);
		}else {
			holder = (ClientHolder) view.getTag();
		}
		holder.bind(getItem(position));
		client = list.get(position);
		return view;
	}

	public class ClientHolder{
		@Bind(R.id.tvId) TextView tvId;
		@Bind(R.id.tvName) TextView tvName;
		@Bind(R.id.tvService) TextView tvService;
		@Bind(R.id.tvPayment) TextView tvPayment;
		@Bind(R.id.btEdit) Button btEdit;
		public ClientHolder(View view){
			ButterKnife.bind(this, view);
		}
		public void bind(final Client client){
			tvId.setText("KH 00" + String.valueOf(client.getId()));
			tvName.setText(client.getName());
			tvService.setText(client.getService());
			tvPayment.setText(client.getPayment());

			btEdit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent iEdit = new Intent(v.getContext(), EditActivity.class);
					iEdit.putExtra("clientId", client.getId());
					v.getContext().startActivity(iEdit);
				}
			});
		}
	}
}
