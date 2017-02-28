package com.example.mxhung.managerinternet.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mxhung.managerinternet.R;
import com.example.mxhung.managerinternet.activity.DetailActivity;
import com.example.mxhung.managerinternet.adapter.ClientAdapter;
import com.example.mxhung.managerinternet.manager.DBManager;
import com.example.mxhung.managerinternet.model.Client;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

	@Bind(R.id.tvEmpty)
	TextView tvEmpty;
	@Bind(R.id.lvListClient)
	ListView lvClient;
	@Bind(R.id.etSearch)
	EditText etSearch;
	@Bind(R.id.ibtSpeech)
	ImageButton ibtSpeech;
	ArrayList<Client> list;
	ClientAdapter adapter;
	private int clientId;
	private final int REQ_CODE_SPEECH_INPUT = 100;
	public ListFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, container, false);
		ButterKnife.bind(this, view);
		loadListClient();


		textChange();
		lvClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent iDetail = new Intent(getActivity(), DetailActivity.class);
				clientId = list.get(position).getId();
				iDetail.putExtra("clientId", clientId);
				startActivity(iDetail);
			}
		});

		lvClient.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Thông Báo");
				builder.setIcon(R.drawable.ic_warning);
				builder.setMessage("Bạn có chắc chắn muốn xóa không?");
				builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						clientId = list.get(position).getId();
						if (DBManager.INSTANCE.deleteClient(clientId) != -1) {
							Toast.makeText(getActivity(), "Xóa thành công " + list.get(position).getName(), Toast.LENGTH_SHORT).show();
							loadListClient();
						} else {
							Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
						}
					}
				});
				builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.create().show();
				return true;
			}
		});
		lvClient.setLongClickable(true);



		return view;
	}

	public void textChange(){
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				etSearch.setSelection(etSearch.getText().length());
				list = DBManager.INSTANCE.searchClient(String.valueOf(s));
				if (list.size() == 0){
					tvEmpty.setVisibility(View.VISIBLE);
				} else {
					tvEmpty.setVisibility(View.GONE);
				}
					setAdapterListView(list);
				//adapter.notifyDataSetChanged();
			}
		});
	}
	@OnClick(R.id.ibtSpeech)
	public void speech(){
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_somthing));
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);;
		}catch (ActivityNotFoundException a){
			Toast.makeText(getActivity(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case REQ_CODE_SPEECH_INPUT: {
				if (resultCode == Activity.RESULT_OK && data != null) {
					ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					if ((result.get(0).equals("xóa"))){
						etSearch.setText("");
					}else {
						etSearch.setText(result.get(0));
					}
				}
				break;
			}
		}
	}

	@OnClick(R.id.btClean)
	public void cleanText(){
		etSearch.setText("");
	}

	public void setAdapterListView(ArrayList<Client> list){
		adapter = new ClientAdapter(this, list);
		lvClient.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	public void loadListClient() {
		list = new ArrayList<Client>();
		list.addAll(DBManager.INSTANCE.getListClient());
		if (list.size() == 0){
			tvEmpty.setVisibility(View.VISIBLE);
		} else {
			tvEmpty.setVisibility(View.GONE);
		}
		setAdapterListView(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
