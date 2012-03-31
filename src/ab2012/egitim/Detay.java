package ab2012.egitim;

import ab2012.egitim.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Detay extends Activity {
	String adSoyad;
	TextView tvAdSoyad,tvTelefon,tvEmail,tvIsyeri;
	int kisiId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detay);
		
		tvAdSoyad = (TextView)findViewById(R.id.tvAdSoyad);
		tvTelefon = (TextView)findViewById(R.id.tvTelefon);
		tvEmail = (TextView)findViewById(R.id.tvEmail);
		
	
		
		kisiId = getIntent().getIntExtra("KisiId", -1);
		
		KisiDetay(kisiId);
		/*
		adSoyad = getIntent().getExtras().getString("adSoyad");
		if (!adSoyad.trim().equals("")) {
			tvAdSoyad.setText(adSoyad);
		}
		*/
	}
	
	private void KisiDetay(int kisiId2) {
		
		
		

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
		
		
	
	
		
		
		
		tvAdSoyad.setText(adSoyad);
		tvTelefon.setText(telefon);
		tvEmail.setText(email);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = new MenuInflater(this);
		mi.inflate(R.menu.detaymenu, menu);
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.sil) {
			KisiSil();
			
		}else if (item.getItemId() == R.id.duzenle) {
			KisiDuzelt();
			
		}
		return super.onOptionsItemSelected(item);
	}

	private void KisiDuzelt() {
		//Toast.makeText(this, "Düzenlenecek", Toast.LENGTH_SHORT).show();
		Intent intentEkle = new Intent(this,KisiEkleAct.class);
		intentEkle.putExtra("KisiId", kisiId);
		startActivity(intentEkle);
		
	}

	private void KisiSil() {
		AlertDialog.Builder alertYapici = new AlertDialog.Builder(this);
		alertYapici.setTitle("Silme İşlemi");
		alertYapici.setMessage("Kaydı silmek istediğinizden emin misiniz?");
		alertYapici.setPositiveButton("Sil gitsin", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Sil();
			}
		});
		alertYapici.setNegativeButton("Vazgeçtim", null);
		AlertDialog uyari = alertYapici.create();
		
		uyari.show();
		
		
		
	}
	
	private void Sil(){
		DbHelper myDbHelper = new DbHelper(this);
		SQLiteDatabase db = myDbHelper.getWritableDatabase();
		String where = "KisiId="+kisiId;
		int eks = db.delete("tbKisi", where, null);
		if (eks == 1) {
			Toast.makeText(this, "Sildim gitti", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(this, "Silinemedi", Toast.LENGTH_SHORT).show();
		}
	}

}
