package it.unisa.guessthecover;

import java.util.ArrayList;
import database.DbAdapter;
import database.DbAdapterFilm;
import database.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FilmActivity extends Activity {
private ArrayList<Film> filmList=new ArrayList<Film>();
private Film film;
private String help = "";
private int np;
private int multiplier;
private int numhelp=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cover);
		
		Intent intent=getIntent(); // l'intent di questa activity
		
		Log.d("FilmActivity", "Intent intent=getIntent();");
        String pkg=getPackageName();
		
		film=(Film)intent.getSerializableExtra(pkg+".myFilm");
		multiplier=film.getMultiplier(); //prendo moltiplicatore
		
		ImageView imageFilm = (ImageView)findViewById(R.id.cover);
   	 	Log.d("lista film","prendo il film selezionato");

   	 	String uri = "drawable/" + film.getNameImage();
   	  	Log.d("lista film","contenuto uri: "+uri);
   	  	int imageResource = getResources().getIdentifier(uri, "drawable", getPackageName());
   	  	Log.d("lista film","imageResource: "+imageResource+" numero immagine:"+film.getNameImage());
   	  	Drawable image = getResources().getDrawable(imageResource);
   	  	Log.d("lista film","contenuto Drawable image: "+image);
   	  	imageFilm.setImageDrawable(image);
		
   	  	Button conferma=(Button)findViewById(R.id.conferma);
   	  	conferma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText titolo = (EditText)findViewById(R.id.title);
				
		        if(titolo.getText().toString().equalsIgnoreCase(film.getTitle())){
		        	//salva sul bd
		        	updateUser(film.getPoint()*multiplier);
		        	updateFilm();
		        	creaToast("Point +"+(film.getPoint()*multiplier));
		        	multiplier++;
		        	/*
		        	try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {e.printStackTrace();}
		        	*/
		        	String pkg=getPackageName(); 
		        	Intent intent = new Intent(getApplicationContext(), FilmListActivity.class);
		        	Category cat=new Category(film.getCategory(),0); //0 è un valore inutile serve solo per creare l'oggetto poichè utilizzeremo solo il nome della categoria
		        	cat.setMultiplier(multiplier);
		        	intent.putExtra(pkg+".myCategory", cat); //invio dati compreso moltiplicatore 
		        	startActivity(intent);
		        	finish();
		        }  
		        else{
		        	//messaggio di avviso 
		        	creaToast("Hai Sbagliato");
		        	multiplier=1;
		        }
		        
			}
		});
   	    
   	  	Button aiuto=(Button)findViewById(R.id.help);
	  	aiuto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int na=film.getTitle().length()/3;
				Log.d("AIUTO","na="+na);
				Log.d("AIUTO","numhelp="+numhelp);
				if(numhelp<2){
					numhelp++;
					Log.d("AIUTO","numhelp="+numhelp);
					help= help + film.getTitle().substring(help.length(),help.length()+na);
					Log.d("AIUTO","caratteri da aggiungere "+film.getTitle().substring(help.length(),help.length()+na));
					Log.d("AIUTO","help="+help);
					EditText titolo=(EditText)findViewById(R.id.title);
					titolo.setText(help);
					int p;
					if(numhelp==1)p=-10;
					else p=-35;
					
					Log.d("AIUTO","punti persi="+p);
					updateUser(p);
					np=np+p;
					Log.d("AIUTO","punti persi in totale="+np);
					creaToast(p+"Point");
					multiplier=1;
				}
				else{
					creaToast("Non posso più aiutarti");
				}
			}
	  	});
		
	  	Button soluzione=(Button)findViewById(R.id.solution);
	  	soluzione.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText titolo=(EditText)findViewById(R.id.title);
				titolo.setText(film.getTitle());
				int p=-100;
				updateUser(p);
				creaToast("point "+ p);
			}
	  	});
	}
	
	private void creaToast(String s){
		Toast toast=Toast.makeText(this,s,Toast.LENGTH_LONG);
    	toast.show();
	}
	
	private void updateFilm(){
		DbAdapterFilm dbHelper;
		
   	 	dbHelper = new DbAdapterFilm(getApplicationContext());
   	 	dbHelper.open();
   	 	dbHelper.updateContact(film.getId(), film.getTitle(), film.getCategory(), film.getPoint(), film.getImage(), film.getNameImage(), film.getTitle());
   	 	dbHelper.close();
	}
	
	private void updateUser(int point){
		DbAdapter dbHelperUser; 
		Cursor cursorUser;
   	 	
   	 	dbHelperUser = new DbAdapter(getApplicationContext());
	 	dbHelperUser.open();
	 	
		cursorUser=dbHelperUser.fetchAllContacts();
		
		cursorUser.moveToFirst();
		
		dbHelperUser.updateContact(
	 			cursorUser.getInt(cursorUser.getColumnIndex(DbAdapter.KEY_ID)),
	 			cursorUser.getString(cursorUser.getColumnIndex(DbAdapter.KEY_EMAIL)),
	 			cursorUser.getString(cursorUser.getColumnIndex(DbAdapter.KEY_NAME)),
	 			//cursorUser.getString(cursorUser.getColumnIndex(DbAdapter.KEY_PASSWORD)),
	 			cursorUser.getInt(cursorUser.getColumnIndex(DbAdapter.KEY_POINT))+point );
	 	
	 	cursorUser.close();
	 	dbHelperUser.close();
	}
	
	@SuppressWarnings("unused")
	private String getUser(){
		DbAdapter dbHelperUser; 
		Cursor cursorUser;
		
   	 	dbHelperUser = new DbAdapter(getApplicationContext());
	 	dbHelperUser.open();
	 	
	 	
	 	cursorUser=dbHelperUser.fetchAllContacts();
	 	cursorUser.moveToFirst();
	 	String s= cursorUser.getString(cursorUser.getColumnIndex(DbAdapter.KEY_EMAIL));
	 			
	 	cursorUser.close();
	 	dbHelperUser.close();
		return s;
	}
	
	private ArrayList<Film> creaListaFilm(String categoria){
		ArrayList<Film> lista=new ArrayList<Film>();
		DbAdapterFilm dbHelper; 
   	 	Cursor cursor;
   	 	
   	 	Log.d("creaListaFilm","sto per fare la roba sul bd");
		dbHelper = new DbAdapterFilm(getApplicationContext());
		
		Log.d("creaListaFilm","apro il bd");
		dbHelper.open();
		Log.d("creaListaFilm","ho aperto il bd");
   	 	
		Log.d("creaListaFilm","riempio il cursore");
   	 	cursor = dbHelper.fetchContactsByFilter(categoria);
   	 	Log.d("creaListaFilm","selezionata la categoria "+categoria);
   	 	int i=0;
   	 	while(cursor.moveToNext()){
   	 		lista.add(new Film(
   	 				cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
   	 				cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
   	 				cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
   	 				cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
   	 				cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
   	 				cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_NAMEIMAGE)),
   	 				cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
   	 		Log.d("creaListaFilm",lista.get(i).toString());
   	 		i++;
   	 	}
   	 	cursor.close();
   	 	dbHelper.close();
		return lista;
	}

	@Override
	public void onBackPressed() {
		Intent intent;
		intent = new Intent(getApplicationContext(), FilmListActivity.class);
		String pkg=getPackageName();
		Category cat=new Category(film.getCategory(),0); //0 è un valore inutile serve solo per creare l'oggetto poichè utilizzeremo solo il nome della categoria
    	cat.setMultiplier(multiplier);
    	intent.putExtra(pkg+".myCategory", cat); //invio dati compreso moltiplicatore 
		startActivity(intent);
		finish();
	}
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
	        menu.add("Logout");
	        return true; 
	}
	static final int Logout= 0;
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				UserFunctions f=new UserFunctions();
				f.logoutUser(getBaseContext());
				Intent intent;
				intent = new Intent(getApplicationContext(), LoginActivity.class);
				String pkg=getPackageName();
				intent.putExtra(pkg+".myMultiplier", multiplier); //invio moltiplicatore
				startActivity(intent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
    }

}