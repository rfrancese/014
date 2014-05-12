package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapterFilm {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = DbAdapterFilm.class.getSimpleName();
         
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelperFilm dbHelper;
 
	// Database fields
	private static final String DATABASE_TABLE      = "cover";
	public static final String KEY_CONTACTID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_POINT = "point";
	public static final String KEY_IMAGE = "image";
	public static final String KEY_GUESS = "guess";
	
	
	public DbAdapterFilm(Context context) {
		this.context = context;
	}
 
	public DbAdapterFilm open() throws SQLException {
		dbHelper = new DatabaseHelperFilm(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
 
	public void close() {
		dbHelper.close();
	}
 
	private ContentValues createContentValues(String title, String category, int point, int image, String guess ) {
		ContentValues values = new ContentValues();
		values.put( KEY_TITLE, title );
		values.put( KEY_CATEGORY, category );
		values.put( KEY_POINT, point );
		values.put( KEY_IMAGE, image );
		values.put( KEY_GUESS, guess );
		
		return values;
	}
        
	//create a contact
	public long createContact(String title, String category, int point, int image, String guess ) {
		ContentValues initialValues = createContentValues(title, category, point, image, guess );
		return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
	}	
 
	//update a contact
	public boolean updateContact( long contactID, String title, String category, int point, int image, String guess ) {
		ContentValues updateValues = createContentValues(title, category, point, image, guess);
		return database.update(DATABASE_TABLE, updateValues, KEY_CONTACTID + "=" + contactID, null) > 0;
	}
                 
	//delete a contact      
	public boolean deleteContact(long contactID) {
		return database.delete(DATABASE_TABLE, KEY_CONTACTID + "=" + contactID, null) > 0;
	}
 
	//fetch all contacts
	public Cursor fetchAllContacts() {
		return database.query(DATABASE_TABLE, new String[] { KEY_CONTACTID, KEY_TITLE, KEY_CATEGORY, KEY_POINT, KEY_IMAGE, KEY_GUESS}, null, null, null, null, null);
	}
	
	//fetch contacts filter by a string
	public Cursor fetchContactsByFilter(String filter) {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
			KEY_CONTACTID, KEY_TITLE, KEY_CATEGORY, KEY_POINT, KEY_IMAGE, KEY_GUESS },KEY_CATEGORY + " like '%"+ filter + "%'", null, null, null, null, null); 
         
		return mCursor;
	}
	//scelco la colonna e il valore 
	public Cursor fetchContactsByFilter(String colonna, String filter) {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
			KEY_CONTACTID, KEY_TITLE, KEY_CATEGORY, KEY_POINT, KEY_IMAGE, KEY_GUESS },colonna + " like '%"+ filter + "%'", null, null, null, null, null);  
         
		return mCursor;
	}
}

