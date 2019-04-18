package dev.goodjob.wordmemorizationaid;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import static   dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.DB_TABLE;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ExapleSentence;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ID;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.Word;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.PoSProExp;
import static dev.goodjob.wordmemorizationaid.NewsListEditActivity.clickItemUrl;


public class wordListActivity extends AppCompatActivity {

    public String createTableSql = "CREATE TABLE IF NOT EXISTS" + "\""+ clickItemUrl + "\"" + "(" +ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + Word + " TEXT,"
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


        db = new DatabaseNewsActivity(wordListActivity.this);
        dynamicallyCreateTable(db,createTableSql);



        alWords = new ArrayList<String>();
        lvWordlistWordsAdd = findViewById(R.id.lvWordlistWordsAdd);
        viewData();

        lvWordlistWordsAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(wordListActivity.this, WordMemorizeViewPager.class);
                        intent.putExtra("PARTICULAR_PAGE",position);
                        DatabaseNewsActivity.DB_TABLE = "\""+NewsListEditActivity.clickItemUrl + "\"";
                        populateWord();
                        startActivity(intent);

            }
        });
        lvWordlistWordsAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                final String selectedItem = parent.getItemAtPosition(position).toString();
                final AlertDialog.Builder builder = new AlertDialog.Builder(wordListActivity.this);
                builder.setMessage("Do you want to remove " + selectedItem + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(selectedItem);
                        if(db.deleteEntry(pos))
                        {
                            Log.d(TAG, "onItemLongClick: the postion is "+ pos);
                            Log.d(TAG, "onItemLongClick: " + "entry " + selectedItem + " has been deleted from" + db.DB_TABLE);
                        }
                        else{
                            Log.d(TAG, "onItemLongClick: the postion is "+ pos);
                            Log.d(TAG, "onItemLongClick: " + "entry " + selectedItem + " has not been deleted from" + db.DB_TABLE);
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(
                                getApplicationContext(),
                                selectedItem + " has been removed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // Create and show the dialog
                builder.show();
                // Signal OK to avoid further processing of the long click
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabWorlistWordAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(wordListActivity.this, addWordItemActivity.class);
                startActivity(intent);

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(wordListActivity.this);
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
        Log.d(TAG, "dynamicallyCreateTable: the clickOnUrl is" + clickItemUrl);
        SQLiteDatabase dp = db.getWritableDatabase();
        dp.execSQL(sqlInstr);
        Log.d(TAG, "dynamicallyCreateTable: Table " +DB_TABLE+" has been instantiated" );
        dp.close();
    }


    public void populateWord() {
        Cursor cursor = db.viewData();
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


}
