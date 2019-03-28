package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordMemorizeActivity extends AppCompatActivity {

    private static final String TAG = "WordMemorizeActivity";


    ListView lvWordMemorize;
    ArrayList<String> alWordMemorize;
    ArrayAdapter adapter;
    DatabaseHelperActivity db = new DatabaseHelperActivity(this);
    DatabaseNewsActivity dbNews = new DatabaseNewsActivity(this);
    // to create Newslist
//    DatabaseNewsActivity dbForWordAdd = new DatabaseNewsActivity(this);;//to put data into WordAdder


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_memorize);

        alWordMemorize = new ArrayList<String>();
        lvWordMemorize = findViewById(R.id.lvWordMemorize);

        viewData();

//        populateWord();

//        dbForWordAdd = new DatabaseNewsActivity(this);


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




    private void populateWord() {
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



}
