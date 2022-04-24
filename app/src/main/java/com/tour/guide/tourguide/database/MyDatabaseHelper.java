package com.tour.guide.tourguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "CountriesDB";
    private static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";
    public static String COUNTRY_NAME = "countryName";
    public static String CURRENCY = "currency";
    public static String DESCRIPTION = "description";
    public static String TYPE = "type";
    public static String FAVORITE = "fStatus";
    private static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COUNTRY_NAME+" VARCHAR(500),"+CURRENCY+" VARCHAR(500),"+DESCRIPTION+" VARCHAR(500),"+TYPE+" VARCHAR(500), "+FAVORITE+" INTEGER );";

    private Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(CREATE_TABLE);
        }
        catch (Exception e)
        {
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertData(String countryName, String currency, String description,String type, int favorite)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COUNTRY_NAME,countryName);
        contentValues.put(CURRENCY,currency);
        contentValues.put(DESCRIPTION,description);
        contentValues.put(TYPE,type);
        contentValues.put(FAVORITE,favorite);
        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowId;
    }

    public Cursor read_all_data(String countryName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+ " WHERE "+COUNTRY_NAME+"='"+countryName+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null,null);
        return cursor;
    }

    public void updateData(String countryName, String currency, String description, String type,int favorite)
    {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COUNTRY_NAME,countryName);
        contentValues.put(CURRENCY,currency);
        contentValues.put(DESCRIPTION,description);
        contentValues.put(TYPE,type);
        contentValues.put(FAVORITE,favorite);
        sqLiteDatabase.update(TABLE_NAME,contentValues,COUNTRY_NAME+" = ?",new String[]{countryName});
    }

    public Cursor read_all_favorites() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+ " WHERE "+FAVORITE+"=1";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null,null);
        return cursor;
    }
}
