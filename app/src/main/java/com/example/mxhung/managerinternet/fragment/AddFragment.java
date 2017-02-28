package com.example.mxhung.managerinternet.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
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
	@Bind(R.id.btAdd)
	Button btAdd;
	final static int FASH151 = 200;
	final static int FASH156 = 1140;
	final static int FASH1512 = 2160;
	final static int FASH201 = 240;
	final static int FASH206 = 1320;
	final static int FASH2012 = 2400;
	final static int FASH301 = 280;
	final static int FASH306 = 1560;
	final static int FASH3012 = 2880;
	Calendar c = Calendar.getInstance();

	public AddFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add, container, false);
		ButterKnife.bind(this,view);
		String[] arrayCity = getResources().getStringArray(R.array.arrayCity);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayCity);
		etCity.setThreshold(3);
		etCity.setAdapter(adapter);
		return view;
	}

	@OnClick(R.id.btAdd)
	public void add(){
		String name = etName.getText().toString();
		String phone = etPhone.getText().toString();
		String address = etAddress.getText().toString();
		String city = etCity.getText().toString();
		if (!name.matches("[\\w\\s,]{3,25}") || (name.trim().equals(""))){
			etName.requestFocus();
			etName.selectAll();
			Toast.makeText(getActivity(), "\t\t\t\t\t\tMời nhập lại\n\tTÊN KHÁCH HÀNG\t chỉ chứa các ký tự, có độ dài lớn hơn 3", Toast.LENGTH_LONG).show();
			return;
		}else if (!phone.matches("(09[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(01[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])")){
			etPhone.setText("");
			etPhone.requestFocus();
			Toast.makeText(getActivity(), "\t\t\t\t\t\tMời nhập lại\n\tSỐ ĐIỆN THOẠI\t không hợp lệ", Toast.LENGTH_LONG).show();
			return;
		}else if (!address.matches("[\\w\\s,]{3,25}") || address.trim().equals("")){
			etAddress.setText("");
			etAddress.requestFocus();
			Toast.makeText(getActivity(), "\t\t\t\t\t\tMời nhập lại\n\tĐỊA CHỈ\t chỉ chứa các ký tự, có độ dài lớn hơn 3", Toast.LENGTH_LONG).show();
			return;
		}else if (!city.matches("[\\w\\s,]{3,25}") || city.trim().equals("")){
			etAddress.setText("");
			etAddress.requestFocus();
			Toast.makeText(getActivity(), "\t\t\t\t\t\tMời nhập lại\n\tTỈNH\t chỉ chứa các ký tự, có độ dài lớn hơn 3", Toast.LENGTH_LONG).show();
			return;
		}

		int idService = rdgService.getCheckedRadioButtonId();
		int idPayment = rdgPayment.getCheckedRadioButtonId();
		if (idService == -1){
			Toast.makeText(getActivity(), "Bạn phải chọn gói dịch vụ", Toast.LENGTH_SHORT).show();
			return;
		}else if (idPayment == -1){
			Toast.makeText(getActivity(), "Bạn phải chọn hình thức thanh toán", Toast.LENGTH_SHORT).show();
			return;
		}
		//radio is checked
		RadioButton rd_service = (RadioButton)getView().findViewById(idService);
		RadioButton rd_payment = (RadioButton)getView().findViewById(idPayment);

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
		client.setName(name);
		client.setPhone(Integer.parseInt(phone));
		client.setAddress(address);
		client.setCity(city);
		client.setService(rd_service.getText().toString());
		client.setPayment(rd_payment.getText().toString());
		client.setStartDate(startDate);
		client.setEndDate(endDate);
		client.setMoney(String.valueOf(money2));
		client.setNote(note);
		client.setStatus(0);
		DBManager.INSTANCE.addClient(client);
		Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();

		etName.setText("");
		etName.requestFocus();
		etAddress.setText("");
		etCity.setText("");
		etPhone.setText("");
		rdgPayment.clearCheck();
		rdgService.clearCheck();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Thông tin khách hàng");
		builder.setIcon(R.drawable.ic_client);
		builder.setPositiveButton("ĐÓNG", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		String msg = "Tên khách hàng: " + name + "\n";
		msg += "SĐT khách hàng: " + phone + "\n";
		msg += "Địa chỉ khách hàng: " + address + ", " + city + "\n";
		msg += "Ngày đăng ký: " + startDate + "\n";
		msg += "Gói dịch vụ: " + (rd_service.getText().toString()) + "\n";
		msg += "Thanh toán: " + (rd_payment.getText().toString()) + "\n";

		builder.setMessage(msg);
		builder.create().show();
	}

	public void getCity(){
		String[] arrayCity = getResources().getStringArray(R.array.arrayCity);
		ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrayCity);
		etCity.setAdapter(adapter);
	}


}
