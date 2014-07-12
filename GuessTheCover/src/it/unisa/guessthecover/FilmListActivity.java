package it.unisa.guessthecover;

import java.util.ArrayList;
import java.util.HashMap;

import database.DbAdapterFilm;
import database.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FilmListActivity extends Activity {
	private int multiplier;
	private ArrayList<Film> filmList=new ArrayList<Film>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		
		setContentView(R.layout.activity_category);
		
		Intent intent=getIntent(); // l'intent di questa activity
        String pkg=getPackageName();
        Category categoria=(Category) intent.getSerializableExtra(pkg+".myCategory");
        Log.d("FilmListActivity","prendo i dati passati "+categoria);
        
        multiplier=categoria.getMultiplier();	//prendiamo moltiplicatore
        filmList=creaListaFilm(categoria.getName());
        
        final ArrayList<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>(); 
   	 	
   	 	for(int i=0;i<filmList.size();i++){
   	 		Film p=filmList.get(i);// per ogni film                    
   	 		HashMap<String,Object> filmMap=new HashMap<String, Object>();//creiamo una mappa di valori
               
   	 		filmMap.put("id", p.getId());
   	 		filmMap.put("title", p.getTitle());
   	 		filmMap.put("category", p.getCategory());
   	 		filmMap.put("point", p.getPoint()); 
   	 		filmMap.put("image", p.getImage()); 
   	 		filmMap.put("nameImage", p.getNameImage()); 
   	 		filmMap.put("guess", p.getGuess()); 
		
   	 		data.add(filmMap);  //aggiungiamo la mappa di valori alla sorgente dati
   	 	}
   	 	
     String[] from={"point","image","guess"}; //dai valori contenuti in queste chiavi
     int[] to={R.id.filmPoint,R.id.filmImage,R.id.filmName};//agli id delle view (id del frammento della lista)
     
     
     SimpleAdapter adapter=new SimpleAdapter(
             getApplicationContext(),
             data,//sorgente dati
             R.layout.film_element, //layout contenente gli id di "to"
             from,
             to);

     		//utilizzo dell'adapter
     ListView listaFilm=(ListView)findViewById(R.id.categoryListView);
     listaFilm.setAdapter(adapter);
   	 	
     listaFilm.setOnItemClickListener(new OnItemClickListener(){
     	@Override
 		public void onItemClick(AdapterView<?> listView, View itemView, int position,long itemId) {
     		Log.d("ListViewActivity.onCreate", "Hai selezionato " + listView.getItemAtPosition(position));
       	 	Log.d("ListViewActivity.onCreate", "con id = "+itemId+" e position = "+position);
       	 	
       	 	
       	 	HashMap<String, Object> filmMap = data.get(position);
       	 	Film film=new Film((int)filmMap.get("id"),(String)filmMap.get("title")
       	 			,(String)filmMap.get("category"),(int)filmMap.get("point")
       	 			,(int)filmMap.get("image"),(String)filmMap.get("nameImage")
       	 			,(String)filmMap.get("guess"));
       	 	
       	 	film.setMultiplier(multiplier);
       	 	if(film.getGuess().equalsIgnoreCase(film.getTitle())){
       	 		creaToast("Hai già indovinato questa cover");
       	 	}
       	 	else{
       	 		String pkg=getPackageName(); 
       	 		Intent intent;
       	 		intent = new Intent(getApplicationContext(), FilmActivity.class);
       		
       	 		intent.putExtra(pkg+".myFilm", film);      //inseriamo i dati nell'intent  //inviamo moltiplicatore
       		
       	 		startActivity(intent);
       	 		finish();
       	 	}
     	}
     });    

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
		intent = new Intent(getApplicationContext(), Gioco.class);
		String pkg=getPackageName();
		intent.putExtra(pkg+".myNewMultiplier", multiplier);
		startActivity(intent);
		finish();
	}
	private void creaToast(String s){
		Toast toast=Toast.makeText(this,s,Toast.LENGTH_LONG);
    	toast.show();
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