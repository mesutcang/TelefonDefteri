package ab2012.egitim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{

	static String DatabaseAdi = "telefondefteri";
	static int versiyon =1;
	
	public DbHelper(Context context) {
		
		super(context, DatabaseAdi, null, versiyon);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE tbKisi " +
				"(KisiId INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
				"AdSoyad TEXT NOT NULL , Telefon TEXT NOT NULL , Email TEXT)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
