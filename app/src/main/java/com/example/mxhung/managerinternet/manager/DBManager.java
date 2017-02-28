package com.example.mxhung.managerinternet.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mxhung.managerinternet.model.Bill;
import com.example.mxhung.managerinternet.model.Client;

import java.util.ArrayList;

/**
 * Created by MXHung on 6/14/2016.
 */
public enum  DBManager {
	INSTANCE;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "client.db";

	private static final String TABLE_CLIENT = "client";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PHONE = "phone";
	private static final String COLUMN_ADDRESS = "address";
	private static final String COLUMN_CITY = "city";
	private static final String COLUMN_SERVICE = "service";
	private static final String COLUMN_PAYMENT = "payment";
	private static final String COLUMN_START_DATE = "start_date";
	private static final String COLUMN_END_DATE = "end_date";
	private static final String COLUMN_MONEY = "money";
	private static final String COLUMN_NOTE = "note";
	private static final String TABLE_BILL = "bill";
	private static final String COLUMN_BID = "bId";
	private static final String COLUMN_PAYMENT_DATE = "payment_date";
	private static final String COLUMN_STATUS = "status";
	private SQLiteDatabase db;
	private DatabaseHelper databaseHelper;
	public static  void init (Context context){
		if(INSTANCE.databaseHelper == null){
			INSTANCE.databaseHelper = new DatabaseHelper(context);
		}
	}
	public void addClient (Client client){
		db = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, client.getName());
		values.put(COLUMN_PHONE, client.getPhone());
		values.put(COLUMN_ADDRESS, client.getAddress());
		values.put(COLUMN_CITY, client.getCity());
		values.put(COLUMN_SERVICE, client.getService());
		values.put(COLUMN_PAYMENT, client.getPayment());
		values.put(COLUMN_START_DATE, client.getStartDate());
		values.put(COLUMN_END_DATE, client.getEndDate());
		values.put(COLUMN_MONEY, client.getMoney());
		values.put(COLUMN_NOTE, client.getNote());
		values.put(COLUMN_STATUS, client.getStatus());
		db.insert(TABLE_CLIENT, null, values);
		db.close();
	}

	public void addBill (Bill bill){
		db = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_BID, bill.getbId());
		values.put(COLUMN_NAME, bill.getName());
		values.put(COLUMN_SERVICE, bill.getService());
		values.put(COLUMN_PAYMENT, bill.getPayment());
		values.put(COLUMN_PAYMENT_DATE, bill.getPaymentDate());
		values.put(COLUMN_MONEY, bill.getMoney());
		values.put(COLUMN_STATUS, bill.getStatus());
		db.insert(TABLE_BILL, null, values);
		db.close();
	}

	public int editClient (Client client){
		db = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, client.getName());
		values.put(COLUMN_PHONE, client.getPhone());
		values.put(COLUMN_ADDRESS, client.getAddress());
		values.put(COLUMN_CITY, client.getCity());
		values.put(COLUMN_SERVICE, client.getService());
		values.put(COLUMN_PAYMENT, client.getPayment());
		values.put(COLUMN_START_DATE, client.getStartDate());
		values.put(COLUMN_END_DATE, client.getEndDate());
		values.put(COLUMN_MONEY, client.getMoney());
		values.put(COLUMN_NOTE, client.getNote());
		values.put(COLUMN_STATUS, client.getStatus());
		return db.update(TABLE_CLIENT,values,COLUMN_ID + " =?", new String[]{String.valueOf(client.getId())});
	}

	public int edit (Client client){
		db = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, client.getName());
		values.put(COLUMN_PHONE, client.getPhone());
		Log.d("---", String.valueOf(db.update(TABLE_CLIENT,values,COLUMN_ID + " =?", new String[]{String.valueOf(client.getId())})));
		return db.update(TABLE_CLIENT,values,COLUMN_ID + " =?", new String[]{String.valueOf(client.getId())});
	}
	public ArrayList<Client> searchClient(String s){
		db = databaseHelper.getWritableDatabase();
		ArrayList<Client> list = new ArrayList<>();
		String sql = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_ADDRESS + ", " + COLUMN_CITY + ", " + COLUMN_PHONE + ", " + COLUMN_PAYMENT + ", " + COLUMN_SERVICE
				+ " FROM "+ TABLE_CLIENT + " WHERE " + COLUMN_NAME + " LIKE '%" + s + "%'"
				+ " OR " + COLUMN_ADDRESS + " LIKE '%" + s + "%'"
				+ " OR " + COLUMN_CITY + " LIKE '%" + s + "%'"
				+ " OR " + COLUMN_PHONE + " LIKE '%" + s + "%'";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Client client = new Client();
			client.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
			client.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
			client.setPhone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))));
			client.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
			client.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
			client.setPayment(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT)));
			client.setService(cursor.getString(cursor.getColumnIndex(COLUMN_SERVICE)));

			list.add(client);
			cursor.moveToNext();
		}
		return list;
	}

	public ArrayList<Client> getListClient(){
		ArrayList<Client> list = new ArrayList<Client>();
		db = databaseHelper.getWritableDatabase();
		String sql = "SELECT * FROM " + TABLE_CLIENT;
		Cursor cursor = db.rawQuery(sql,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Client client = new Client();
			client.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
			client.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
			client.setService(cursor.getString(cursor.getColumnIndex(COLUMN_SERVICE)));
			client.setPayment(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT)));
			client.setPhone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))));
			client.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
			client.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
			client.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE)));
			client.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE)));
			client.setMoney(cursor.getString(cursor.getColumnIndex(COLUMN_MONEY)));
			client.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
			client.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))));
			list.add(client);
			cursor.moveToNext();
		}
		return list;
	}

	public ArrayList<Bill> getListBill(){
		ArrayList<Bill> list = new ArrayList<Bill>();
		db = databaseHelper.getWritableDatabase();
		String sql = "SELECT * FROM " + TABLE_BILL;
		Cursor cursor = db.rawQuery(sql,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Bill bill = new Bill();
			bill.setbId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BID))));
			bill.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
			bill.setService(cursor.getString(cursor.getColumnIndex(COLUMN_SERVICE)));
			bill.setPayment(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT)));
			bill.setPaymentDate(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_DATE)));
			bill.setMoney(cursor.getString(cursor.getColumnIndex(COLUMN_MONEY)));
			bill.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))));
			list.add(bill);
			cursor.moveToNext();
		}
		return list;
	}
	public Client getListClientId(int id){
		db = databaseHelper.getWritableDatabase();
		String sql = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + COLUMN_ID + " = " + id;
		Cursor cursor = db.rawQuery(sql,null);
		Client client = new Client();
		if (cursor.moveToFirst()){
			client.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
			client.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
			client.setService(cursor.getString(cursor.getColumnIndex(COLUMN_SERVICE)));
			client.setPayment(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT)));
			client.setPhone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))));
			client.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
			client.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
			client.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE)));
			client.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE)));
			client.setMoney(cursor.getString(cursor.getColumnIndex(COLUMN_MONEY)));
			client.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
			client.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))));
		}
		return client;
	}

	public int deleteClient(int id){
		db = databaseHelper.getWritableDatabase();
		return db.delete(TABLE_CLIENT, COLUMN_ID + " =? ", new String[]{String.valueOf(id)} );

	}
	private static class DatabaseHelper extends SQLiteOpenHelper{
		private SQLiteDatabase mDatabase;
		public DatabaseHelper(Context context){
			super(context,"/storage/emulated/0/ListClient/" + DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public synchronized void close() {
			if (mDatabase != null){
				mDatabase.close();
			}
			super.close();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String tableClient = "CREATE TABLE " + TABLE_CLIENT + " ( "
					+ COLUMN_ID
					+ " Integer primary key autoincrement, "
					+ COLUMN_NAME
					+ " TEXT, "
					+ COLUMN_PHONE
					+ " INTERGER, "
					+ COLUMN_ADDRESS
					+ " TEXT, "
					+ COLUMN_CITY
					+ " TEXT, "
					+ COLUMN_SERVICE
					+ " TEXT, "
					+ COLUMN_PAYMENT
					+ " TEXT, "
					+ COLUMN_START_DATE
					+ " TEXT, "
					+ COLUMN_END_DATE
					+ " TEXT, "
					+ COLUMN_MONEY
					+ " REAL, "
					+ COLUMN_NOTE
					+ " TEXT, "
					+ COLUMN_STATUS
					+ " INTEGER "
					+ " ) ";
			String tableBill = "CREATE TABLE " + TABLE_BILL + "( "
					+ COLUMN_BID
					+ " Integer primary key, "
					+ COLUMN_NAME
					+ " TEXT, "
					+ COLUMN_SERVICE
					+ " TEXT, "
					+ COLUMN_PAYMENT
					+ " TEXT, "
					+ COLUMN_MONEY
					+ " REAL, "
					+ COLUMN_PAYMENT_DATE
					+ " TEXT, "
					+ COLUMN_STATUS
					+ " INTEGER "
					+ " )";
			db.execSQL(tableClient);
			db.execSQL(tableBill);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
			onCreate(db);
		}
	}
}
