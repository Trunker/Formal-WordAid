package dev.goodjob.wordmemorizationaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseNewsActivity extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseNewsActivity";
    public static ArrayList<Integer> idArrayList = new ArrayList<>();

    public static final String DB_NAME = "Words_db";
//    public static String Initial_DB_TABLE = "Initial_Table";
    public static String DB_TABLE = "\""+NewsListEditActivity.clickItemUrl + "\"";
    public static final String ID = "_Id";
    public static final String IDD = "_Idd";
    public static final String Word = "Word";
    public static final String ExapleSentence = "ExampleSentence";
    public static final String PoSProExp = "PoSProExp";

    public static  String CREATE_TABLE = "CREATE TABLE "+ "\""+NewsListEditActivity.clickItemUrl + "\""+ "(" +ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + ""+ Word + " TEXT,"
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
        long result = db.insert(DB_TABLE, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM "+ DB_TABLE ;
        Log.d(TAG, "viewData: From"+ DB_TABLE);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean deleteEntry(int id){
        this.saveID();
        SQLiteDatabase db = this.getWritableDatabase();
        String _idd = Integer.toString(idArrayList.get(id));
        Log.d(TAG, "deleteEntry: The id of this entry is"+_idd);
        long result = db.delete(DB_TABLE,  "_id=?", new String[]{_idd});
        Log.d(TAG, "deleteEntry: the status code is: "+ result);
        Log.d(TAG, "deleteEntry: the current table is "+ DB_TABLE);

        db.close();
        return result == 1;
    }

    public void saveID(){                                                                           //for storing the sequence of the autoincremented id
        String sql = "Select * FROM "+ DB_TABLE ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null && cursor.getCount()>0){
            idArrayList.removeAll(idArrayList);
            while(cursor.moveToNext()){
                idArrayList.add(cursor.getInt(0));
            }
            cursor.close();
            db.close();
        }
    }
}
