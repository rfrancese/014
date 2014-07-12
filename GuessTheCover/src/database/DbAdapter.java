package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
 
public class DbAdapter {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = DbAdapter.class.getSimpleName();
         
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
 
	// Database fields
	private static final String TABLE_LOGIN = "login";
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_UID = "uid";
	public static final String KEY_POINT = "point";
	//public static final String KEY_PASSWORD = "password";
	
	
	public DbAdapter(Context context) {
		this.context = context;
	}
 
	public DbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
 
	public void close() {
		dbHelper.close();
	}
 
	//private ContentValues createContentValues(String email, String nickname, String password, int point ) {
	private ContentValues createContentValues(String email, String nickname, int point ) {
		ContentValues values = new ContentValues();
		values.put( KEY_EMAIL, email );
		values.put( KEY_NAME, nickname );
		//values.put( KEY_PASSWORD, password );
		values.put( KEY_POINT, point );
		
		return values;
	}
         
	//create a contact
	//public long createContact(String email, String nickname, String password, int point) {
	public long createContact(String email, String nickname, int point) {
		//ContentValues initialValues = createContentValues(email, nickname, password, point);
		ContentValues initialValues = createContentValues(email, nickname, point);
		return database.insertOrThrow(TABLE_LOGIN, null, initialValues);
	}	
 
	//update a contact
	//public boolean updateContact( long contactID, String email, String nickname, String password, int point ) {
		//ContentValues updateValues = createContentValues(email, nickname, password, point);
	public boolean updateContact( long contactID, String email, String nickname, int point ) {
		ContentValues updateValues = createContentValues(email, nickname, point);
		return database.update(TABLE_LOGIN, updateValues, KEY_ID + "=" + contactID, null) > 0;
	}
                 
	//delete a contact      
	public boolean deleteContact(long contactID) {
		return database.delete(TABLE_LOGIN, KEY_ID + "=" + contactID, null) > 0;
	}
 
	//fetch all contacts
	public Cursor fetchAllContacts() {
		//return database.query(TABLE_LOGIN, new String[] { KEY_ID, KEY_EMAIL, KEY_NAME, KEY_PASSWORD, KEY_POINT}, null, null, null, null, null);
		return database.query(TABLE_LOGIN, new String[] { KEY_ID, KEY_EMAIL, KEY_NAME, KEY_POINT}, null, null, null, null, null);
	}
	
   
	//fetch contacts filter by a string
	public Cursor fetchContactsByFilter(String filter) {
		Cursor mCursor = database.query(true, TABLE_LOGIN, new String[] {
				//KEY_ID, KEY_EMAIL, KEY_NAME, KEY_PASSWORD, KEY_POINT },
				KEY_ID, KEY_EMAIL, KEY_NAME, KEY_POINT },
			KEY_EMAIL + " like '%"+ filter + "%'", null, null, null, null, null); //KEY_EMAIL 
         
		return mCursor;
	}
}