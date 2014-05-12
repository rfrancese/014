package it.unisa.guessthecover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import database.DbAdapter;
import database.DbAdapterFilm;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Gioco extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Log.d("Gioco","email o password non corrispondono");   
        //ArrayList<Category> categoryList=new ArrayList<Category>(); //lista delle persone che la listview visualizzerà
          
           
        Category [] category={
        	new Category("fantasy",R.drawable.fantasy),
            new Category("horror", R.drawable.horror),
            new Category("thriller", R.drawable.thriller),
            new Category("drammatico", R.drawable.drammatico),
            new Category("azione", R.drawable.azione),
            new Category("commedia", R.drawable.commedia),
            new Category("animazione", R.drawable.animazione)};
           
        //Questa è la lista che rappresenta la sorgente dei dati della listview
        //ogni elemento è una mappa(chiave->valore)
        ArrayList<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>();
          
          
        for(int i=0;i<category.length;i++){
        	
        	Category p=category[i];// per ogni categoria                    
            HashMap<String,Object> categoryMap=new HashMap<String, Object>();//creiamo una mappa di valori
                  
            categoryMap.put("image", p.getImage()); // per la chiave image, inseriamo la risorsa dell immagine
            categoryMap.put("name", p.getName()); // per la chiave name,l'informazine sul nome
                  
            data.add(categoryMap);  //aggiungiamo la mappa di valori alla sorgente dati
        }
           
        String[] from={"image","name"}; //dai valori contenuti in queste chiavi
        int[] to={R.id.categoryImage};//agli id delle view
        
        SimpleAdapter adapter=new SimpleAdapter(
                getApplicationContext(),
                data,//sorgente dati
                R.layout.category_element, //layout contenente gli id di "to"
                from,
                to);

        		//utilizzo dell'adapter
        ListView lista=(ListView)findViewById(R.id.categoryListView);
        lista.setAdapter(adapter);
        
        
        lista.setOnItemClickListener(new OnItemClickListener(){
        	@Override
    		public void onItemClick(AdapterView<?> listView, View itemView, int position,long itemId) {
        		Log.d("ListViewActivity.onCreate", "Hai selezionato " + listView.getItemAtPosition(position));
           	 	Log.d("ListViewActivity.onCreate", "con id = "+itemId+" e position = "+position);
        		
           	 	
           	 	ArrayList<Film> filmList=new ArrayList<Film>();
           	 	
           	 	DbAdapterFilm dbHelper; 
           	 	Cursor cursor;
           	 	
           	 	Log.d("gioco","sto per fare la roba sul bd");
	 			dbHelper = new DbAdapterFilm(getApplicationContext());
	 			Log.d("gioco","apro il bd");
	 			dbHelper.open();
	 			Log.d("gioco","ho aperto il bd");
           	 	
           	 	switch(position){
           	 		case 0:{
           	 			Log.d("gioco","riempio il cursore");
           	 			cursor = dbHelper.fetchContactsByFilter("fantasy");
           	 			while(cursor.moveToNext()){
           	 				filmList.add(new Film(
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
           	 			}
           	 			cursor.close();
           	 		}
           	 		break;
           	 		case 1:{
           	 			Log.d("gioco","riempio il cursore");
           	 			cursor = dbHelper.fetchContactsByFilter("horror");
           	 			while(cursor.moveToNext()){
           	 				filmList.add(new Film(
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
           	 			}
       	 				cursor.close();
           	 		}
           	 		break;
           	 		case 2:{
           	 			Log.d("gioco","riempio il cursore");
           	 			cursor = dbHelper.fetchContactsByFilter("thriller");
           	 			while(cursor.moveToNext()){
           	 				filmList.add(new Film(
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
           	 						cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
           	 						cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
           	 			}
           	 			cursor.close();
           	 		}
           	 		break;
           	 		case 3:{
       	 				Log.d("gioco","riempio il cursore");
       	 				cursor = dbHelper.fetchContactsByFilter("drammatico");
       	 				while(cursor.moveToNext()){
       	 					filmList.add(new Film(
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
       	 				}
       	 				cursor.close();
           	 		}
           	 		break;
           	 		case 4:{
       	 				Log.d("gioco","riempio il cursore");
       	 				cursor = dbHelper.fetchContactsByFilter("azione");
       	 				while(cursor.moveToNext()){
       	 					filmList.add(new Film(
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
       	 				}
       	 				cursor.close();
           	 		}
           	 		break;
           	 		case 5:{
       	 				Log.d("gioco","riempio il cursore");
       	 				cursor = dbHelper.fetchContactsByFilter("commedia");
       	 				while(cursor.moveToNext()){
       	 					filmList.add(new Film(
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
       	 				}
       	 				cursor.close();
           	 		}
           	 		break;
           	 		case 6:{
       	 				Log.d("gioco","riempio il cursore");
       	 				cursor = dbHelper.fetchContactsByFilter("animazione");
       	 				while(cursor.moveToNext()){
       	 					filmList.add(new Film(
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_CONTACTID)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_TITLE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_CATEGORY)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_POINT)),
       	 							cursor.getInt(cursor.getColumnIndex(DbAdapterFilm.KEY_IMAGE)),
       	 							cursor.getString(cursor.getColumnIndex(DbAdapterFilm.KEY_GUESS)) ));
       	 				}
       	 				cursor.close();
           	 		}
           	 		break;
           	 		default:{
           	 			dbHelper.close();
           	 		}
           	 		break;
           	 	}
           	 	//aprire la nuova lista con i film 
           	 	
           	 	ArrayList<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>(); 
             
           	 	for(int i=0;i<filmList.size();i++){
           	 		Film p=filmList.get(i);// per ogni film                    
           	 		HashMap<String,Object> filmMap=new HashMap<String, Object>();//creiamo una mappa di valori
                       
           	 		filmMap.put("id", p.getId());
           	 		filmMap.put("title", p.getTitle());
           	 		filmMap.put("category", p.getCategory());
           	 		filmMap.put("point", p.getPoint()); 
           	 		filmMap.put("image", p.getImage()); 
           	 		filmMap.put("guess", p.getGuess()); 
   	 		
           	 		data.add(filmMap);  //aggiungiamo la mappa di valori alla sorgente dati
           	 	}
             /*
           	 String[] from={"image","name","surname"}; //dai valori contenuti in queste chiavi
             int[] to={R.id.personImage,R.id.personName,R.id.personSurname};//agli id delle view
           	 */
           	 	
             String[] from={"point","image","title"}; //dai valori contenuti in queste chiavi
             int[] to={R.id.filmPoint,R.id.filmImage};//agli id delle view (id del frammento della lista)
             
             
             SimpleAdapter adapter=new SimpleAdapter(
                     getApplicationContext(),
                     data,//sorgente dati
                     R.layout.film_element, //layout contenente gli id di "to"
                     from,
                     to);

             		//utilizzo dell'adapter
             ListView lista=(ListView)findViewById(R.id.categoryListView);
             lista.setAdapter(adapter);
           	 	
           	 	
           	 	/*
           	 	Intent intent;
    			intent = new Intent(Gioco.this, Home.class);
    			startActivity(intent);
    			*/
    			
    		}
    	});
        
	}
	
}
