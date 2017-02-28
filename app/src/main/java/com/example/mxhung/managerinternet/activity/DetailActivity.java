package com.example.mxhung.managerinternet.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mxhung.managerinternet.R;
import com.example.mxhung.managerinternet.manager.DBManager;
import com.example.mxhung.managerinternet.model.Bill;
import com.example.mxhung.managerinternet.model.Client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
	@Bind(R.id.tvClientId)
	TextView tvClientId;
	@Bind(R.id.tvName)
	TextView tvName;
	@Bind(R.id.tvPhone)
	TextView tvPhone;
	@Bind(R.id.tvAddress)
	TextView tvAddress;
	@Bind(R.id.tvPayment)
	TextView tvPayment;
	@Bind(R.id.tvService)
	TextView tvService;
	@Bind(R.id.tvMoney)
	TextView tvMoney;
	@Bind(R.id.tvNote)
	TextView tvNote;
	@Bind(R.id.tvStartDate)
	TextView tvStartDate;
	@Bind(R.id.tvEndDate)
	TextView tvEndDate;
	@Bind(R.id.chkBill)
	CheckBox chkBill;
	private int clientId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		ButterKnife.bind(this);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		clientId = getIntent().getIntExtra("clientId", 0);
		Client client = new Client();
		client = DBManager.INSTANCE.getListClientId(clientId);
		if (client.getStatus() == 1){
			chkBill.setChecked(true);
			chkBill.setEnabled(false);
		}
		tvClientId.setText("KH 00" + String.valueOf(clientId));
		tvName.setText(client.getName());
		tvPhone.setText(String.valueOf(client.getPhone()));
		tvAddress.setText(client.getAddress() + ", " + client.getCity());
		tvService.setText(client.getService());
		tvPayment.setText(client.getPayment());
		tvStartDate.setText(client.getStartDate());
		tvEndDate.setText(client.getEndDate());
		tvMoney.setText(client.getMoney() + " VNĐ");
		tvNote.setText(client.getNote());
		final Client finalClient = client;
		chkBill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Calendar cal = Calendar.getInstance();
					String paymentDate = dateFormat.format(cal.getTime());
					Toast.makeText(getApplicationContext(), "Đã thanh toán" , Toast.LENGTH_LONG).show();
					chkBill.setEnabled(false);
					Bill bill = new Bill();
					bill.setbId(clientId);
					bill.setName(finalClient.getName());
					bill.setPayment(finalClient.getPayment());
					bill.setService(finalClient.getService());
					bill.setMoney(finalClient.getMoney());
					bill.setPaymentDate(paymentDate);
					bill.setStatus(1);
					finalClient.setStatus(1);
					DBManager.INSTANCE.addBill(bill);
					DBManager.INSTANCE.editClient(finalClient);
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home){
			finish();
		}
		return true;
	}

}
