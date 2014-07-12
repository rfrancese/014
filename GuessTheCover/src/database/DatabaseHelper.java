package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;
    // Lo statement SQL di creazione del database
    //private static final String DATABASE_CREATE = "create table contact (_id INTEGER PRIMARY KEY, email TEXT, nickname TEXT, password TEXT, point NUMERIC);";
    private static final String TABLE_LOGIN = "login";
    
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_POINT = "point";
    //private static final String KEY_PASSWORD = "password";
    
    // Costruttore
    public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Questo metodo viene chiamato durante la creazione del database
    @Override
    public void onCreate(SQLiteDatabase db) {
           // database.execSQL(DATABASE_CREATE);
    	String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_UID + " TEXT,"
                + KEY_POINT + " INTEGER" +")";
               // + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    // Questo metodo viene chiamato durante l'upgrade del database, ad esempio quando viene incrementato il numero di versione
    @Override
    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
             
            database.execSQL("DROP TABLE IF EXISTS contact");
            onCreate(database);
    }
}
