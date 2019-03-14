package dev.goodjob.wordmemorizationaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperActivity extends SQLiteOpenHelper {
    public static final String DB_NAME = "New_db";
    public static final String DB_TABLE = "News_Table";

    public static final String ID = "_Id";
    public static final String Title = "Title";
    public static final String Date = "Date";
    public static final String Url = "Url";

    public DatabaseHelperActivity(Context context){
        super(context, DB_NAME, null, 1);
    }

    public static final String CREATE_TABLE = "CREATE TABLE "+ DB_TABLE + "(" + ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ Title + " TEXT,"
            + Date +" TEXT," + Url + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
    }

    public boolean insertData(String title, String date, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Title, title);
        contentValues.put(Date, date);
        contentValues.put(Url, url);
        long result = db.insert(DB_TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM "+ DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
