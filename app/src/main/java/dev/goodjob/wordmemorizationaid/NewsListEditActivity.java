package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsListEditActivity extends AppCompatActivity {
    private static final String TAG = "NewsListEditActivity";

    public static String clickItemUrl;


    ListView lvNews;                                                                                // for listing all news items
    ArrayList<String> alNews;                                                                       // get data from news items database
    ArrayAdapter adapter;                                                                           // an adapter used to put data from arraylist into listView
    DatabaseHelperActivity db1 = new DatabaseHelperActivity(this); // a newsitem instance


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_edit);


        alNews = new ArrayList<String>();
        lvNews = findViewById(R.id.lvnewsListEditAdd);

        viewData();
        Button  btnNewsListItemsAdd= findViewById(R.id.btnNewsListItemsAdd);
        btnNewsListItemsAdd.setOnClickListener(new View.OnClickListener() {                         //add listener for "add" button
        @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsListEditActivity.this, addNewsItems.class);
                startActivity(intent);
            }
    });
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getUrl(lvNews.getItemAtPosition(position).toString());
                Intent intent = new Intent(NewsListEditActivity.this, NewsListItemsWebView.class);
                startActivity(intent);
            }
        });

        lvNews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getUrl(lvNews.getItemAtPosition(position).toString());
                DatabaseNewsActivity.DB_TABLE = "\""+NewsListEditActivity.clickItemUrl + "\"";
            Intent intent = new Intent(NewsListEditActivity.this, wordListActivity.class);
            startActivity(intent);
                Log.d(TAG, "onItemLongClick: URl is"+ clickItemUrl);
                return false;
        }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        alNews.clear();
        viewData();
        Toast.makeText(this,"Come on", Toast.LENGTH_SHORT).show();
    }

    public void viewData(){
        Cursor cursor = db1.viewData();

        if(cursor.getCount()==0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                alNews.add(cursor.getString(1).replaceAll("\"", "").replaceAll("##", ""));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alNews);
            lvNews.setAdapter(adapter);

        }
        cursor.close();
    }


    public void getUrl(String itemTitle){
        Cursor cursor = db1.viewData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }while (cursor.moveToNext()){
            String cursorString = cursor.getString(1).replaceAll("\"","");


            Log.d(TAG, "cursorString is "+cursorString);
            Log.d(TAG, "itemTitle is "+itemTitle);

            if(itemTitle.equalsIgnoreCase(cursorString)){
                clickItemUrl = cursor.getString(3).replaceAll("\"","");
                Log.d(TAG, "equal is true");
                Log.d(TAG, "getUrl: "+clickItemUrl);
            }
        }
        cursor.close();
    }





}

