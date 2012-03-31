package ab2012.egitim;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KisiEkleAct extends Activity implements OnClickListener{
	Button btnKisiEkle;
	EditText etAdSoyad,etTelefon,etEmail,etIsyeri;
	String adSoyad,telefon,email,isyeri;
	int kisiId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kisiekle);
		btnKisiEkle = (Button)findViewById(R.id.btnKisiEkle);
		btnKisiEkle.setOnClickListener(this);
		 etAdSoyad = (EditText)findViewById(R.id.etAdSoyad);
		 etTelefon = (EditText)findViewById(R.id.etTelefon);
		 etEmail = (EditText)findViewById(R.id.etEmail);
		 etIsyeri = (EditText)findViewById(R.id.etIsyeri);
		 
		 Intent gelenIntent = getIntent();
		 kisiId = gelenIntent.getIntExtra("KisiId", -1);
		 if (kisiId != -1) {
			KisiBilgileriniGetir(kisiId);
		}
		 
		 
		 
	}
	private void KisiBilgileriniGetir(int kisiId2) {
		/*
		String adSoyad = "Mehmet Aca";
		String telefon = "05353123155";
		String email = "mehmet@ceturk.com";
		*/
		
		DbHelper myDbHelper = new DbHelper(this);
		
		SQLiteDatabase db = myDbHelper.getReadableDatabase();
		String where = "KisiId="+kisiId2;
		Cursor cursorKisi =db.query("tbKisi", null, where, null, null, null, null);
		
		cursorKisi.moveToFirst();
		
		//adsoyad işlemi
		int adSoyadIndex = cursorKisi.getColumnIndex("AdSoyad"); 
		String adSoyad = cursorKisi.getString(adSoyadIndex);
		
		//telefon işlemi
		int telefonIndex = cursorKisi.getColumnIndex("Telefon"); 
		String telefon = cursorKisi.getString(telefonIndex);
		
		//email işlemi
		int emailIndex = cursorKisi.getColumnIndex("Email"); 
		String email = cursorKisi.getString(emailIndex);
		
		
		
		
		etAdSoyad.setText(adSoyad);
		etTelefon.setText(telefon);
		etEmail.setText(email);
		
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnKisiEkle) {
			
			KaydetIslemi();
			
			
		}
		
	}
	public void KaydetIslemi() {
		if (kisiId==-1) {
			//Yeni kayıt
			InsertIslemi();
			
		}else {
			//kisiId'ye göre update yapılacak
			UpdateIslemi();
			
		}
		
		isyeri = etIsyeri.getText().toString();
		if (!adSoyad.trim().equals("") &&
				!telefon.trim().equals("") &&
					!email.trim().equals("")){
			
			
		}else {
			Toast.makeText(this, "Bütün alanların dolduruması gerekmektedir.", Toast.LENGTH_SHORT).show();
		}
	}
	private void UpdateIslemi() {
		//kisiId 'ye göre update
		adSoyad = etAdSoyad.getText().toString();
		telefon = etTelefon.getText().toString();
		email = etEmail.getText().toString();
		
		DbHelper myDbHelper = new DbHelper(this);
		SQLiteDatabase db = myDbHelper.getWritableDatabase();
		String where = "KisiId=" +kisiId;
		
		ContentValues cv = new ContentValues();
		cv.put("AdSoyad", adSoyad);
		cv.put("Telefon", telefon);
		cv.put("Email", email);
		
		
		int eks =db.update("tbKisi", cv, where, null);
		if (eks ==1) {
			Toast.makeText(this, "update yapıldı", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(this, "update yapılamadı.", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	private void InsertIslemi() {
		adSoyad = etAdSoyad.getText().toString();
		telefon = etTelefon.getText().toString();
		email = etEmail.getText().toString();
		
		DbHelper myDbHelper = new DbHelper(this);
		
		SQLiteDatabase db = myDbHelper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("AdSoyad", adSoyad);
		cv.put("Telefon", telefon);
		cv.put("Email", email);
		
		long sonuc =db.insert("tbKisi", null, cv);
		db.close();
		
	
		
		if (sonuc != -1) {
			Toast.makeText(this, "insert yaptık", Toast.LENGTH_SHORT).show();
			Intent intentListe = new Intent(this,TelefonDefteriActivity.class);
			startActivity(intentListe);
		}else {
			Toast.makeText(this, "insert hata oldu", Toast.LENGTH_SHORT).show();
		}
		
		
		
	}

}
