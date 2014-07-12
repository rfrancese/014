package it.unisa.guessthecover;

import java.util.ArrayList;
import java.util.HashMap;
import database.DbAdapterFilm;
import database.UserFunctions;
import android.annotation.SuppressLint;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Gioco extends Activity {
	private int multiplier;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		//se il bd è vuoto lo popolo 
		DbAdapterFilm dbHelper; 
		Cursor cursor;
		
	    dbHelper = new DbAdapterFilm(getApplicationContext());
	    dbHelper.open();
        cursor = dbHelper.fetchAllContacts();
        if(cursor.moveToNext()==false){
        	popola();
        }
        cursor.close();
        dbHelper.close();
		//fine popolamento 
		super.onCreate(savedInstanceState);
		// remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_category);
        Log.d("Gioco","email o password non corrispondono");   
        //ArrayList<Category> categoryList=new ArrayList<Category>(); //lista delle persone che la listview visualizzerà
          
           
        Category [] category={
        	new Category("fantasy",R.drawable.fantasy),
            new Category("horror", R.drawable.horror),
            //new Category("thriller", R.drawable.thriller),
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
        
        
        Intent intent=getIntent(); // l'intent di questa activity
        String pkg=getPackageName();
        int mult=intent.getIntExtra(pkg+".myMultiplier",1); //prendi moltiplicatore
        multiplier=mult;
        
        lista.setOnItemClickListener(new OnItemClickListener(){
        	@Override
    		public void onItemClick(AdapterView<?> listView, View itemView, int position,long itemId) {
        		Log.d("ListViewActivity.onCreate", "Hai selezionato " + listView.getItemAtPosition(position));
           	 	Log.d("ListViewActivity.onCreate", "con id = "+itemId+" e position = "+position);
        		
           	 	
           	 	
           	 	
           	 	String pkg=getPackageName(); 
           	 	switch(position){
           	 		case 0:{
           			
           	 			
           	 			Log.d("gioco.categoria", "sono in fantasy e sto per fare l'intent");
           	 			Intent intent;
           	 			Log.d("gioco.categoria", "intent");
           	 			intent = new Intent(Gioco.this, FilmListActivity.class); //invia moltiplicatore
           	 			Log.d("gioco.categoria", "intent = new Intent(getApplicationContext(), FilmListActivity.class);");
           	 			//per rendere univoci i nomi delle chiavi passate
           	 			//è consigliato (la doc dice 'must') aggiungere il nome del nostro package davanti al nome
           	 			
           	 			Category cat=new Category("fantasy",R.drawable.fantasy);
           	 			cat.setMultiplier(multiplier); 				//inviamo moltiplicatore
           	 			intent.putExtra(pkg+".myCategory", cat);      //inseriamo i dati nell'intent   
           	 			
           	 			
           	 			Log.d("gioco.categoria", "intent.putExtra(pkg+.myCategory, cat);");
           	 			startActivity(intent);
           	 			Log.d("gioco.categoria", "startActivity(intent);");
           	 		}
           	 		break;
           	 		case 1:{
           	 			Intent intent;
           	 			intent = new Intent(getApplicationContext(), FilmListActivity.class);
           	 			Category cat=new Category("horror", R.drawable.horror);
           	 			cat.setMultiplier(multiplier);
           	 			intent.putExtra(pkg+".myCategory", cat);      //inseriamo i dati nell'intent      
           	 			startActivity(intent);
           	 		}
           	 		break;
           	 		/*case 2:filmList=creaListaFilm("thriller");
           	 		break;*/
           	 		case 2:{
           	 				Intent intent;
           	 				intent = new Intent(getApplicationContext(), FilmListActivity.class);
           	 				Category cat=new Category("drammatico", R.drawable.drammatico);
           	 				cat.setMultiplier(multiplier);
           	 				intent.putExtra(pkg+".myCategory", cat);
           	 				startActivity(intent);
           	 		}
           	 		break;
           	 		case 3:{
       	 				Intent intent;
       	 				intent = new Intent(getApplicationContext(), FilmListActivity.class);
       	 				Category cat=new Category("azione", R.drawable.azione);
       	 				cat.setMultiplier(multiplier);
       	 				intent.putExtra(pkg+".myCategory", cat);
       	 				startActivity(intent);
           	 			
           	 		}
           	 		break;
           	 		case 4:{
       	 				Intent intent;
       	 				intent = new Intent(getApplicationContext(), FilmListActivity.class);
       	 				Category cat=new Category("commedia", R.drawable.commedia);
       	 				cat.setMultiplier(multiplier);
       	 				intent.putExtra(pkg+".myCategory", cat);   
       	 				startActivity(intent);
           	 		}
           	 		break;
           	 		case 5:{
       	 				Intent intent;
       	 				intent = new Intent(getApplicationContext(), FilmListActivity.class);
       	 				Category cat=new Category("animazione", R.drawable.animazione);
       	 				cat.setMultiplier(multiplier);
       	 				intent.putExtra(pkg+".myCategory", cat); 
       	 				startActivity(intent);
           	 		}
           	 		break;
           	 		default:
           	 		break;
           	 	}
           	 	//aprire la nuova lista con i film 
           	 	
           	 	finish();	
        	}
        });
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
				intent = new Intent(Gioco.this, LoginActivity.class);
				String pkg=getPackageName();
				intent.putExtra(pkg+".myMultiplier", multiplier); //invio moltiplicatore
				startActivity(intent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
    }
	
	
	private void popola(){
		DbAdapterFilm dbHelper; 
				dbHelper = new DbAdapterFilm(getApplicationContext());
				dbHelper.open();
				//cursor = dbHelper.fetchContactsByFilter("fantasy");
				dbHelper.createContact("captain america", "fantasy", 15, R.drawable.captain_america_il_soldato_d_inverno,"captain_america_il_soldato_d_inverno", "__________");
				dbHelper.createContact("constantine", "fantasy", 15, R.drawable.constantine,"constantine", "__________");
				dbHelper.createContact("divergent", "fantasy", 30, R.drawable.divergent,"divergent", "__________");
				dbHelper.createContact("edge of tomorrow", "fantasy", 15, R.drawable.edge_of_tomorrow_senza_domani,"edge_of_tomorrow_senza_domani", "__________");
				dbHelper.createContact("enders game", "fantasy", 15, R.drawable.enders_game,"enders_game", "__________");
				dbHelper.createContact("godzilla", "fantasy", 15, R.drawable.godzilla,"godzilla", "__________");
				dbHelper.createContact("harry potter e la camera dei segreti", "fantasy", 15, R.drawable.harry_potter_e_la_camera_dei_segreti,"harry_potter_e_la_camera_dei_segreti", "__________");
				dbHelper.createContact("hellboy 2", "fantasy", 15, R.drawable.hellboy_2_the_golden_army,"hellboy_2_the_golden_army", "__________");
				dbHelper.createContact("i frankenstein", "fantasy", 15, R.drawable.frankenstein,"frankenstein", "__________");
				dbHelper.createContact("il grinch", "fantasy", 15, R.drawable.il_grinch,"il_grinch", "__________");
				dbHelper.createContact("il pianeta delle scimmie", "fantasy", 15, R.drawable.il_pianeta_delle_scimmie,"il_pianeta_delle_scimmie", "__________");
				dbHelper.createContact("il signore degli anelli le due torri", "fantasy", 15, R.drawable.il_signore_degli_anelli_le_due_torri,"il_signore_degli_anelli_le_due_torri", "__________");
				dbHelper.createContact("interstellar", "fantasy", 15, R.drawable.interstellar,"interstellar", "__________");
				dbHelper.createContact("lo hobbit la desolazione di smaug", "fantasy", 15, R.drawable.lo_hobbit_la_desolazione_di_smaug,"lo_hobbit_la_desolazione_di_smaug", "__________");
				dbHelper.createContact("maleficent", "fantasy", 15, R.drawable.maleficent,"maleficent", "__________");
				dbHelper.createContact("percy jackson", "fantasy", 15, R.drawable.percy_jackson,"percy_jackson", "__________");
				dbHelper.createContact("scontro tra titani", "fantasy", 15, R.drawable.scontro_tra_titani,"scontro_tra_titani", "__________");
				dbHelper.createContact("the amazing spiderman 2", "fantasy", 15, R.drawable.the_amazing_spider_man_2,"the_amazing_spider_man2", "__________");
				dbHelper.createContact("thor 3", "fantasy", 15, R.drawable.thor_3,"thor3", "__________");
				dbHelper.createContact("transcendence", "fantasy", 15, R.drawable.transcendence,"transcendence", "__________");
				dbHelper.createContact("transformers 4", "fantasy", 15, R.drawable.transformers_4_l_era_dell_estinzione,"transformers_4_l_era_dell_estinzione", "__________");
				dbHelper.createContact("x men giorni di un futuro passato", "fantasy", 15, R.drawable.x_men_giorni_di_un_futuro_passato,"x_men_giorni_di_un_futuro_passato", "__________");			
	
				//horror
				dbHelper.createContact("30 giorni di buio", "horror", 15, R.drawable.trenta_giorni_di_buio,"trenta_giorni_di_buio", "__________");			
				dbHelper.createContact("carrie lo sguardo di satana", "horror", 15, R.drawable.carrie_lo_sguardo_di_satana,"carrie_lo_sguardo_di_satana", "__________");
				dbHelper.createContact("district 9", "horror", 15, R.drawable.district_9,"district_9", "__________");
				dbHelper.createContact("hostel", "horror", 15, R.drawable.hostel,"hostel", "__________");
				dbHelper.createContact("Insidious 2", "horror", 15, R.drawable.insidious_2,"insidious_2", "__________");
				dbHelper.createContact("la casa", "horror", 15, R.drawable.la_casa,"la_casa", "__________");
				dbHelper.createContact("la stirpe del male", "horror", 15, R.drawable.la_stirpe_del_male,"la_stirpe_del_male", "__________");
				dbHelper.createContact("moon", "horror", 15, R.drawable.moon,"moon", "__________");
				dbHelper.createContact("profondo rosso", "horror", 15, R.drawable.profondo_rosso,"profondo_rosso", "__________");
				dbHelper.createContact("quarantena", "horror", 15, R.drawable.quarantena,"quarantena", "__________");
				dbHelper.createContact("saw", "horror", 15, R.drawable.saw,"saw", "__________");
				dbHelper.createContact("stay alive", "horror", 15, R.drawable.stay_alive,"stay_alive", "__________");
				dbHelper.createContact("the divide", "horror", 15, R.drawable.the_divide,"the_divide", "__________");
				dbHelper.createContact("the mist", "horror", 15, R.drawable.the_mist,"the_mist", "__________");
				dbHelper.createContact("the_purge2", "horror", 15, R.drawable.the_purge2,"the_purge2", "__________");
				dbHelper.createContact("wolfman", "horror", 15, R.drawable.wolfman,"wolfman", "__________");
				
				//thrillern
				
				//drammatico
				dbHelper.createContact("all is lost", "drammatico", 15, R.drawable.all_is_lost,"all_is_lost", "__________");
				dbHelper.createContact("american hustle", "drammatico", 15, R.drawable.american_hustle,"american_hustle", "__________");
				dbHelper.createContact("her", "drammatico", 15, R.drawable.her,"her", "__________");
				dbHelper.createContact("il cigno nero", "drammatico", 15, R.drawable.il_cigno_nero,"il_cigno_nero", "__________");
				dbHelper.createContact("il curioso caso di benjamin button", "drammatico", 15, R.drawable.il_curioso_caso_di_benjamin_button,"il_curioso_caso_di_benjamin_button", "__________");
				dbHelper.createContact("jobs", "drammatico", 15, R.drawable.jobs,"jobs", "__________");
				dbHelper.createContact("nymphomaniacs", "drammatico", 15, R.drawable.nymphomaniacs,"nymphomaniacs", "__________");
				dbHelper.createContact("storia d'inverno", "drammatico", 15, R.drawable.storia_d_inverno,"storia_d_inverno", "__________");
				dbHelper.createContact("the wolf of wall street", "drammatico", 15, R.drawable.the_wolf_of_wall_street,"the_wolf_of_wall_street", "__________");
				dbHelper.createContact("two mother", "drammatico", 15, R.drawable.two_mother,"two_mother", "__________");
				
				//azione
				dbHelper.createContact("300 l'alba di un impero", "azione", 15, R.drawable.trecento_l_alba_di_un_impero,"trecento_l_alba_di_un_impero", "__________");
				dbHelper.createContact("bad boys", "azione", 15, R.drawable.bad_boys,"bad_boys", "__________");
				dbHelper.createContact("hercules la leggenda_ha_inizio", "azione", 15, R.drawable.hercules_la_leggenda_ha_inizio,"hercules_la_leggenda_ha_inizio", "__________");
				dbHelper.createContact("il cavaliere oscuro", "azione", 15, R.drawable.il_cavaliere_oscuro,"il_cavaliere_oscuro", "__________");
				dbHelper.createContact("il gladiatore", "azione", 15, R.drawable.il_gladiatore,"il_gladiatore", "__________");
				dbHelper.createContact("jack ryan l iniziazione", "azione", 15, R.drawable.jack_ryan_l_iniziazione,"jack_ryan_l_iniziazione", "__________");
				dbHelper.createContact("kick ass 2", "azione", 15, R.drawable.kick_ass_2,"kick_ass_2", "__________");
				dbHelper.createContact("king kong", "azione", 15, R.drawable.king_kong,"king_kong", "__________");
				dbHelper.createContact("machete kills", "azione", 15, R.drawable.machete_kills,"machete_kills", "__________");
				dbHelper.createContact("men in black_3", "azione", 15, R.drawable.men_in_black_3,"men_in_black_3", "__________");
				dbHelper.createContact("need for speed", "azione", 15, R.drawable.need_for_speed,"need_for_speed", "__________");
				dbHelper.createContact("noah", "azione", 15, R.drawable.noah,"noah", "__________");
				dbHelper.createContact("prisoners", "azione", 15, R.drawable.prisoners,"prisoners", "__________");
				dbHelper.createContact("red 2", "azione", 15, R.drawable.red_2,"red_2", "__________");
				dbHelper.createContact("robo cop", "azione", 15, R.drawable.robo_cop,"robo_cop", "__________");
				dbHelper.createContact("rush", "azione", 15, R.drawable.rush,"rush", "__________");
				dbHelper.createContact("the hunger games la ragazza di fuoco", "azione", 15, R.drawable.the_hunger_games_la_ragazza_di_fuoco,"the_hunger_games_la_ragazza_di_fuoco", "__________");
				dbHelper.createContact("un milione di modi per_morire nel west", "azione", 15, R.drawable.un_milione_di_modi_per_morire_nel_west,"un_milione_di_modi_per_morire_nel_west", "__________");
				dbHelper.createContact("v per vendetta", "azione", 15, R.drawable.v_per_vendetta,"v_per_vendetta", "__________");
				
				//commedia
				dbHelper.createContact("i sogni segreti di walter mitty", "commedia", 15, R.drawable.i_sogni_segreti_di_walter_mitty,"i_sogni_segreti_di_walter_mitty", "__________");
				dbHelper.createContact("insieme per forza", "commedia", 15, R.drawable.insieme_per_forza,"insieme_per_forza", "__________");
				dbHelper.createContact("that awkward moment", "commedia", 15, R.drawable.that_awkward_moment,"that_awkward_moment", "__________");
				
				//animazione
				dbHelper.createContact("dragon trainer 2", "animazione", 15, R.drawable.dragon_trainer_2,"dragon_trainer_2", "__________");
				dbHelper.createContact("frozen il regno di ghiaccio", "animazione", 15, R.drawable.frozen_il_regno_di_ghiaccio,"frozen_il_regno_di_ghiaccio", "__________");
				dbHelper.createContact("i puffi", "animazione", 15, R.drawable.i_puffi,"i_puffi", "__________");
				dbHelper.createContact("il libro della giungla", "animazione", 15, R.drawable.il_libro_della_giungla,"il_libro_della_giungla_2", "__________");
				dbHelper.createContact("il re leone", "animazione", 15, R.drawable.il_re_leone,"il_re_leone", "__________");
				dbHelper.createContact("lili e il vagabondo", "animazione", 15, R.drawable.lili_e_i_vagabondo,"lili_e_il_vagabondo", "__________");
				dbHelper.createContact("rio 2", "animazione", 15, R.drawable.rio_2,"rio_2", "__________");
				dbHelper.createContact("the boxtrolls le scatole magiche", "animazione", 15, R.drawable.the_boxtrolls_le_scatole_magiche,"the_boxtrolls_le_scatole_magiche", "__________");
				dbHelper.createContact("the lego movie", "animazione", 15, R.drawable.the_lego_movie,"the_lego_movie", "__________");		
	}
	
	@Override
	public void onBackPressed() {
		Intent intent;
		intent = new Intent(getApplicationContext(), Home.class);
		String pkg=getPackageName();
		intent.putExtra(pkg+".myNewMultiplier", multiplier);
		startActivity(intent);
		finish();
	}
	
}
