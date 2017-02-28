package com.example.mxhung.managerinternet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mxhung.managerinternet.R;
import com.example.mxhung.managerinternet.manager.DBManager;
import com.example.mxhung.managerinternet.model.Client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MXHung on 6/16/2016.
 */
public class EditActivity extends AppCompatActivity {
	@Bind(R.id.etName)
	EditText etName;
	@Bind(R.id.etPhone)
	EditText etPhone;
	@Bind(R.id.etAddress)
	EditText etAddress;
	@Bind(R.id.etCity)
	AutoCompleteTextView etCity;
	@Bind(R.id.rdgService)
	RadioGroup rdgService;
	@Bind(R.id.rdgPayment)
	RadioGroup rdgPayment;
	@Bind(R.id.btEdit)
	Button btAdd;
	@Bind(R.id.rd1month)
	RadioButton rd1month;
	@Bind(R.id.rd6month)
	RadioButton rd6month;
	@Bind(R.id.rd12month)
	RadioButton rd12month;
	@Bind(R.id.rdFast15)
	RadioButton rdFast15;
	@Bind(R.id.rdFast20)
	RadioButton rdFast20;
	@Bind(R.id.rdFast30)
	RadioButton rdFast30;
	private int clientId;
	final static int FASH151 = 200;
	final static int FASH156 = 1140;
	final static int FASH1512 = 2160;
	final static int FASH201 = 240;
	final static int FASH206 = 1320;
	final static int FASH2012 = 2400;
	final static int FASH301 = 280;
	final static int FASH306 = 1560;
	final static int FASH3012 = 2880;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		ButterKnife.bind(this);
		clientId = getIntent().getIntExtra("clientId", 0);
		Client client = new Client();
		client = DBManager.INSTANCE.getListClientId(clientId);
		etName.setText(client.getName());
		etPhone.setText(String.valueOf(client.getPhone()));
		etAddress.setText(client.getAddress());
		etCity.setText(client.getCity());
		if ((client.getPayment()).equals("Hàng Tháng"))
		{
			rd1month.setChecked(true);
		}else if ((client.getPayment()).equals("6 Thang"))
		{
			rd6month.setChecked(true);
		}else if ((client.getPayment()).equals("12 Thang"))
		{
			rd12month.setChecked(true);
		}

		if ((client.getService()).equals("Fast 15"))
		{
			rdFast15.setChecked(true);
		}else if ((client.getService()).equals("Fast 20"))
		{
			rdFast20.setChecked(true);
		}else if ((client.getService()).equals("Fast 30"))
		{
			rdFast30.setChecked(true);
		}
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
		);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@OnClick(R.id.btEdit)
	public void edit(){
		String name = etName.getText().toString();
		String phone = etPhone.getText().toString();
		String address = etAddress.getText().toString();
		String city = etCity.getText().toString();
		if (!name.matches("[\\w\\s,]{3,25}") || (name.trim().equals(""))){
			etName.requestFocus();
			etName.selectAll();
			Toast.makeText(getApplicationContext(), "\t\t\t\t\t\tMời nhập lại\n\tTÊN KHÁCH HÀNG\t chỉ chứa các ký tự, có độ dài lớn hơn 3", Toast.LENGTH_LONG).show();
			return;
		}else if (!phone.matches("(09[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(01[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])")){
			etPhone.setText("");
			etPhone.requestFocus();
			Toast.makeText(getApplicationContext(), "\t\t\t\t\t\tMời nhập lại\n\tSỐ ĐIỆN THOẠI\t không hợp lệ", Toast.LENGTH_LONG).show();
			return;
		}else if (!address.matches("[\\w\\s,]{3,25}") || address.trim().equals("")){
			etAddress.setText("");
			etAddress.requestFocus();
			Toast.makeText(getApplicationContext(), "\t\t\t\t\t\tMời nhập lại\n\tĐỊA CHỈ\t chỉ chứa các ký tự, có độ dài lớn hơn 3", Toast.LENGTH_LONG).show();
			return;
		}else if (!city.matches("[\\w\\s,]{3,25}") || city.trim().equals("")){
			etAddress.setText("");
			etAddress.requestFocus();
			Toast.makeText(getApplicationContext(), "\t\t\t\t\t\tMời nhập lại\n\tTỈNH\t chỉ chứa các ký tự, có độ dài lớn hơn 3", Toast.LENGTH_LONG).show();
			return;
		}

		int idService = rdgService.getCheckedRadioButtonId();
		int idPayment = rdgPayment.getCheckedRadioButtonId();
		if (idService == -1){
			Toast.makeText(getApplicationContext(), "Bạn phải chọn gói dịch vụ", Toast.LENGTH_SHORT).show();
			return;
		}else if (idPayment == -1){
			Toast.makeText(getApplicationContext(), "Bạn phải chọn hình thức thanh toán", Toast.LENGTH_SHORT).show();
			return;
		}
		//radio is checked
		RadioButton rd_service = (RadioButton) findViewById(idService);
		RadioButton rd_payment = (RadioButton) findViewById(idPayment);

		//lay ngay hien tai
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		String startDate = dateFormat.format(cal.getTime());
		String endDate = null;
		double money = 0;
		double money1 = 0;
		double money2 = 0;
		String note = "";
		if (idService == R.id.rdFast15){
			switch (idPayment){
				case R.id.rd1month:
					cal.add(Calendar.MONTH,1);
					endDate = dateFormat.format(cal.getTime());
					money += FASH151;
					break;
				case  R.id.rd6month:
					cal.add(Calendar.MONTH, 6);
					endDate = dateFormat.format(cal.getTime());
					money += FASH156;
					break;
				case  R.id.rd12month:
					cal.add(Calendar.MONTH,12);
					endDate = dateFormat.format(cal.getTime());
					money += FASH1512;
					break;
			}
		}else if (idService == R.id.rdFast20){
			switch (idPayment){
				case R.id.rd1month:
					cal.add(Calendar.MONTH,1);
					endDate = dateFormat.format(cal.getTime());
					money += FASH201;
					break;
				case R.id.rd6month:
					cal.add(Calendar.MONTH,6);
					endDate = dateFormat.format(cal.getTime());
					money += FASH206;
					break;
				case R.id.rd12month:
					cal.add(Calendar.MONTH,12);
					endDate = dateFormat.format(cal.getTime());
					money += FASH2012;
					break;
			}
		}else{
			switch (idPayment){
				case R.id.rd1month:
					cal.add(Calendar.MONTH,1);
					endDate = dateFormat.format(cal.getTime());
					money += FASH301;
					break;
				case R.id.rd6month:
					cal.add(Calendar.MONTH,6);
					endDate = dateFormat.format(cal.getTime());
					money += FASH306;
					break;
				case R.id.rd12month:
					cal.add(Calendar.MONTH,12);
					endDate = dateFormat.format(cal.getTime());
					money += FASH3012;
					break;
			}
		}

		if (city.equals("Hà Nội") || city.equals("TP HCM")){
			money1 = money;
		}else {
			money1 = money - (money * 0.05);
			note += "Khách hàng ở ngoại thành được giảm 5%\n";
		}

		try {
			Date start_diff = dateFormat.parse(startDate);
			Date end_diff = dateFormat.parse(endDate);

			long diff = end_diff.getTime() - start_diff.getTime();
			int diffDate = (int)(diff/(1000 * 60 * 60 * 24));

			if (diffDate > 366){
				money2 = money1 - (money * 0.05);
				note += "Khách hàng dùng trên 1 năm được giảm 5%\n";
			}else {
				money2 = money1;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Client client = new Client();
		client.setId(clientId);
		client.setName(name);
		client.setPhone(Integer.parseInt(phone));
//		client.setAddress(address);
//		client.setCity(city);
//		client.setService(rd_service.getText().toString());
//		client.setPayment(rd_payment.getText().toString());
//		client.setStartDate(startDate);
//		client.setEndDate(endDate);
//		client.setMoney(String.valueOf(money2));
//		client.setNote(note);
//		client.setStatus(0);
		DBManager.INSTANCE.edit(client);
		Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home){
			Intent intent = new Intent(EditActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return true;
	}
}
