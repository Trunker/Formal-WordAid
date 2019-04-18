package dev.goodjob.wordmemorizationaid;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
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
        FloatingActionButton btnNewsListItemsAdd= findViewById(R.id.btnNewsListItemsAdd);
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
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                getUrl(lvNews.getItemAtPosition(position).toString());
                final String selectedItem = parent.getItemAtPosition(position).toString();
                final AlertDialog.Builder builder = new AlertDialog.Builder(NewsListEditActivity.this);
                builder.setMessage("Do you want to remove " + selectedItem + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(selectedItem);
                        db1.deleteEntry(clickItemUrl);

                        Log.d(TAG, "onItemLongClick: "+ "entry"+ clickItemUrl +"has been deleted from"+db1.DB_TABLE);
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


        //for adding words
//        lvNews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                getUrl(lvNews.getItemAtPosition(position).toString());
//                DatabaseNewsActivity.DB_TABLE = "\""+NewsListEditActivity.clickItemUrl + "\"";
//            Intent intent = new Intent(NewsListEditActivity.this, wordListActivity.class);
//            startActivity(intent);
//                Log.d(TAG, "onItemLongClick: URl is"+ clickItemUrl);
//                return false;
//        }
//        });


    }

    @Override
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewsListEditActivity.this);
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

