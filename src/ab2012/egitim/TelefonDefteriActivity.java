package ab2012.egitim;

import java.util.ArrayList;

import android.R.integer;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TelefonDefteriActivity extends ListActivity{
    ArrayList<String> kisiler;
    ArrayList<Integer> alKisilerId;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
       
        kisiler = kisileriGetir();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kisiler);
        setListAdapter(adp);
    }

	private ArrayList<String> kisileriGetir() {
		ArrayList<String> kisi= new ArrayList<String>();
		alKisilerId = new ArrayList<Integer>();
		
		DbHelper myDbHelper = new DbHelper(this);
		
		SQLiteDatabase db = myDbHelper.getReadableDatabase();
		
		Cursor cursorKisi =db.query("tbKisi", null, null, null, null, null, null);
		
		if (cursorKisi.moveToFirst() == true) {
			
			do {
				//adsoyad işlemi
				int adSoyadIndex = cursorKisi.getColumnIndex("AdSoyad"); 
				
				String adSoyad = cursorKisi.getString(adSoyadIndex);
				kisi.add(adSoyad);
				
				//id işlemi
				int kisiIdIndex = cursorKisi.getColumnIndex("KisiId"); 
				
				Integer kisiId = cursorKisi.getInt(kisiIdIndex);
						
				alKisilerId.add(kisiId);
				
				
			} while (cursorKisi.moveToNext() == true);
			
		}else {
			Toast.makeText(this, "Kayıt Bulunamadı!", Toast.LENGTH_SHORT).show();
		}
		
		

		return kisi;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String secilenKisi = kisiler.get(position);
		int kisiId=alKisilerId.get(position);
		
			
		Intent intentDetay = new Intent(this,Detay.class);
		//intentDetay.putExtra("adSoyad", secilenKisi);
		intentDetay.putExtra("KisiId", kisiId);
		startActivity(intentDetay);
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuBaglayici = new MenuInflater(this);
		menuBaglayici.inflate(R.menu.kisieklemenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.kisiEkle) {
			Intent intent_kisiEkle = new Intent(this,KisiEkleAct.class);
			startActivity(intent_kisiEkle);
		}
		return super.onOptionsItemSelected(item);
	}
}