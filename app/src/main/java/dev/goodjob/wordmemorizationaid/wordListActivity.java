package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static   dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.DB_TABLE;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ExapleSentence;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ID;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.PoSProExp;


public class wordListActivity extends AppCompatActivity {

    public String createTableSql = "CREATE TABLE IF NOT EXISTS" + "\""+NewsListEditActivity.clickItemUrl + "\"" + "(" +ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + DatabaseNewsActivity.Word + " TEXT,"
            + ExapleSentence +" TEXT, "+ PoSProExp + " TEXT);"; // do not put this string static

    private static final String TAG = "wordListActivity";

    DatabaseNewsActivity db;
    ListView lvWordlistWordsAdd;
    ArrayAdapter adapter;
    ArrayList<String> alWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseNewsActivity(wordListActivity.this);
        dynamicallyCreateTable(db,createTableSql);



        alWords = new ArrayList<String>();
        lvWordlistWordsAdd = findViewById(R.id.lvWordlistWordsAdd);
        viewData();

        FloatingActionButton fab = findViewById(R.id.fabWorlistWordAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(wordListActivity.this, addWordItemActivity.class);
                startActivity(intent);

            }
        });
    }




    public void viewData(){
        Cursor cursor = db.viewData();

        if(cursor.getCount()==0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                alWords.add(cursor.getString(1).replaceAll("\"", "").replaceAll("##", ""));

            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,alWords);
            lvWordlistWordsAdd.setAdapter(adapter);

        }
        cursor.close();
    }

    protected void onRestart() {
        super.onRestart();
        alWords.clear();
        viewData();
        Toast.makeText(this,"Come on", Toast.LENGTH_SHORT).show();
    }



    public void  dynamicallyCreateTable(DatabaseNewsActivity db, String sqlInstr){
        Log.d(TAG, "dynamicallyCreateTable: the sqlInstr is " + sqlInstr);
        Log.d(TAG, "dynamicallyCreateTable: the clickOnUrl is" + NewsListEditActivity.clickItemUrl);
        SQLiteDatabase dp = db.getWritableDatabase();
        dp.execSQL(sqlInstr);
        Log.d(TAG, "dynamicallyCreateTable: Table " +DB_TABLE+" has been instantiated" );
        dp.close();

    }
}
