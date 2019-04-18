package dev.goodjob.wordmemorizationaid;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.DB_TABLE;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ExapleSentence;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ID;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.PoSProExp;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.Word;
import static dev.goodjob.wordmemorizationaid.NewsListEditActivity.clickItemUrl;

public class WordMemorizeActivity extends AppCompatActivity {

//    public static String wordListActivityOnClickUrl;

    private static final String TAG = "WordMemorizeActivity";
    public String createTableSql = "CREATE TABLE IF NOT EXISTS" + "\""+ clickItemUrl + "\"" + "(" +ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + Word + " TEXT,"
            + ExapleSentence +" TEXT, "+ PoSProExp + " TEXT);"; // do not put this string static


    ListView lvWordMemorize;
    ArrayList<String> alWordMemorize;
    ArrayAdapter adapter;
    DatabaseHelperActivity db = new DatabaseHelperActivity(this);
    DatabaseNewsActivity dbNews = new DatabaseNewsActivity(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_memorize);


        alWordMemorize = new ArrayList<String>();
        lvWordMemorize = findViewById(R.id.lvWordMemorize);

        viewData();


        FloatingActionButton fab = findViewById(R.id.fabWordMemorize);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordMemorizeActivity.this, addNewsItems.class);
                startActivity(intent);
            }
        });


        lvWordMemorize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getUrl(lvWordMemorize.getItemAtPosition(position).toString());
                DatabaseNewsActivity.DB_TABLE = "\""+NewsListEditActivity.clickItemUrl + "\"";
                dynamicallyCreateTable(dbNews,createTableSql);
                Log.d(TAG, "onItemClick: "+NewsListEditActivity.clickItemUrl);
                populateWord();
                Intent intent = new Intent(WordMemorizeActivity.this, WordMemorizeViewPager.class);
                startActivity(intent);
            }
        });


        lvWordMemorize.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getUrl(lvWordMemorize.getItemAtPosition(position).toString());
                DatabaseNewsActivity.DB_TABLE = "\""+NewsListEditActivity.clickItemUrl + "\"";
                Log.d(TAG, "onItemLongClick: NewsListEditActivity.clickItemUrl is" + NewsListEditActivity.clickItemUrl);
                Intent intent = new Intent(WordMemorizeActivity.this, wordListActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals("")) {
                    if (adapter != null) {
                        adapter.getFilter().filter(newText);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WordMemorizeActivity.this);
                        builder.setMessage("There is no item");
                        builder.setCancelable(false);
                        builder.setNegativeButton("I see", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    protected void onRestart() {
        super.onRestart();
        alWordMemorize.clear();
        viewData();
        Toast.makeText(this,"Come on", Toast.LENGTH_SHORT).show();
    }

    public void viewData(){
        Cursor cursor = db.viewData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                alWordMemorize.add(cursor.getString(1).replaceAll("\"", "").replaceAll("##", ""));

            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alWordMemorize);
            lvWordMemorize.setAdapter(adapter);

        }
        cursor.close();
    }

    public void getUrl(String itemTitle){
        Cursor cursor = db.viewData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }while (cursor.moveToNext()){
            String cursorString = cursor.getString(1).replaceAll("\"","");


            Log.d(TAG, "cursorString is "+cursorString);
            Log.d(TAG, "itemTitle is "+itemTitle);

            if(itemTitle.equalsIgnoreCase(cursorString)){
                NewsListEditActivity.clickItemUrl = cursor.getString(3).replaceAll("\"","");
                Log.d(TAG, "equal is true");
                Log.d(TAG, "getUrl: "+ NewsListEditActivity.clickItemUrl);
            }
        }
        cursor.close();
    }

    public void populateWord() {
        Cursor cursor = dbNews.viewData();
        WordDataProvider.clear(); // to clear the word arraylist
        if(cursor.getCount()==0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                WordDataProvider.addWord(cursor.getString(0).replaceAll("\"", "").replaceAll("##", ""),
                        cursor.getString(1).replaceAll("\"", "").replaceAll("##", ""),
                        cursor.getString(2).replaceAll("\"", "").replaceAll("##", ""),
                        cursor.getString(3).replaceAll("\"", "").replaceAll("##", "")
                );
            }
        }
        cursor.close();
    }

    public void  dynamicallyCreateTable(DatabaseNewsActivity db, String sqlInstr){
        Log.d(TAG, "dynamicallyCreateTable: the sqlInstr is " + sqlInstr);
        Log.d(TAG, "dynamicallyCreateTable: the clickOnUrl is" + clickItemUrl);
        SQLiteDatabase dp = db.getWritableDatabase();
        dp.execSQL(sqlInstr);
        Log.d(TAG, "dynamicallyCreateTable: Table " +DB_TABLE+" has been instantiated" );
        dp.close();
    }
}
