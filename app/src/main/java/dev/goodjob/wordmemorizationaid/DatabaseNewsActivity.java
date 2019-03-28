package dev.goodjob.wordmemorizationaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseNewsActivity extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseNewsActivity";

    public static final String DB_NAME = "Words_db";
    public static String DB_TABLE = "Initial_Table";
    public static final String ID = "_Id";
    public static final String Word = "Word";
    public static final String ExapleSentence = "ExampleSentence";
    public static final String PoSProExp = "PoSProExp";

    public static  String CREATE_TABLE = "CREATE TABLE "+ "\""+NewsListEditActivity.clickItemUrl + "\""+ "(" +ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + Word + " TEXT,"
            + ExapleSentence +" TEXT, "+ PoSProExp + " TEXT);";



    public DatabaseNewsActivity(Context context){
        super(context,DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
       }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
    }

    public boolean insertData(String w, String es, String pspep){

        w = w.replaceAll("\"", "##");
        es = es.replaceAll("\"", "##");
        pspep= pspep.replaceAll("\"", "##");
        w = "\"" + w+ "\"";
        es = "\"" + es+ "\"";
        pspep = "\"" + pspep+ "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Word, w);
        contentValues.put(ExapleSentence, es);
        contentValues.put(PoSProExp, pspep);
//        contentValues.put(Explanation, exp);
        long result = db.insert(DB_TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM "+ DB_TABLE ;

       Log.d(TAG, "viewData: From"+ DB_TABLE);
//       try

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

}
