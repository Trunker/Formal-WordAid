package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsListEditActivity extends AppCompatActivity {

    ListView lvNews;
    ArrayList<String> alNews;
    ArrayAdapter adapter;
    DatabaseHelperActivity db1 = new DatabaseHelperActivity(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_edit);


        alNews = new ArrayList<String>();
        lvNews = findViewById(R.id.lvnewsListEditAdd);

        viewData();
//        alNews.clear();
        Button  btnNewsListItemsAdd= findViewById(R.id.btnNewsListItemsAdd);
        btnNewsListItemsAdd.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsListEditActivity.this, addNewsItems.class);
                startActivity(intent);
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
                alNews.add(cursor.getString(1));
//                alNews.add("good");
//                adapter.notifyDataSetChanged();
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alNews);
            lvNews.setAdapter(adapter);

        }

    }


}

