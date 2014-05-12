package database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
 
public class DbAdapter {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = DbAdapter.class.getSimpleName();
         
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
 
	// Database fields
	private static final String DATABASE_TABLE      = "contact";
	public static final String KEY_CONTACTID = "_id";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_NICKNAME = "nickname";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_POINT = "point";
	
	
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
 
	private ContentValues createContentValues(String email, String nickname, String password, int point ) {
		ContentValues values = new ContentValues();
		values.put( KEY_EMAIL, email );
		values.put( KEY_NICKNAME, nickname );
		values.put( KEY_PASSWORD, password );
		values.put( KEY_POINT, point );
		
		return values;
	}
         
	//create a contact
	public long createContact(String email, String nickname, String password, int point) {
		ContentValues initialValues = createContentValues(email, nickname, password, point);
		return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
	}	
 
	//update a contact
	public boolean updateContact( long contactID, String email, String nickname, String password, int point ) {
		ContentValues updateValues = createContentValues(email, nickname, password, point);
		return database.update(DATABASE_TABLE, updateValues, KEY_CONTACTID + "=" + contactID, null) > 0;
	}
                 
	//delete a contact      
	public boolean deleteContact(long contactID) {
		return database.delete(DATABASE_TABLE, KEY_CONTACTID + "=" + contactID, null) > 0;
	}
 
	//fetch all contacts
	public Cursor fetchAllContacts() {
		return database.query(DATABASE_TABLE, new String[] { KEY_CONTACTID, KEY_EMAIL, KEY_NICKNAME, KEY_PASSWORD, KEY_POINT}, null, null, null, null, null);
	}
   
	//fetch contacts filter by a string
	public Cursor fetchContactsByFilter(String filter) {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
			KEY_CONTACTID, KEY_EMAIL, KEY_NICKNAME, KEY_PASSWORD, KEY_POINT },
			KEY_EMAIL + " like '%"+ filter + "%'", null, null, null, null, null); //KEY_EMAIL 
         
		return mCursor;
	}
}