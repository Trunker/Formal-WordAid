package dev.goodjob.wordmemorizationaid;

import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class addNewsItems extends AppCompatActivity {

   public static DatabaseHelperActivity db;
    Button btnaddnewssave;
    EditText tiettitle;
    EditText tietdate;
    EditText tieturl;

//    ListView lvNews;
//    ArrayList<String> alNews;
//    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_items);

        db = new DatabaseHelperActivity(this);
//        alNews = new ArrayList<String>();

//        lvNews = findViewById(R.id.lvnewsListEditAdd);
        tiettitle = findViewById(R.id.tietTitle);
        tietdate = findViewById(R.id.tietDate);
        tieturl = findViewById(R.id.tietUrl);
        btnaddnewssave = findViewById(R.id.btnAddNewsSave);

//        viewData();

        btnaddnewssave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newstitle = tiettitle.getText().toString();
                String newsdate = tietdate.getText().toString();
                String newsurl = tieturl.getText().toString();
                if(! newstitle.equals("")&&!newsurl.equals("")&&!newsdate.equals("")&&db.insertData(newstitle,newsdate,newsurl)){
                    Toast.makeText(addNewsItems.this, "Data Successfully saved", Toast.LENGTH_SHORT).show();
                    tiettitle.setText("");
                    tietdate.setText("");
                    tieturl.setText("");
                }else{
                    Toast.makeText(addNewsItems.this, "Data unsuccessfully saved, Please retry", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

//    public void viewData(){
//        Cursor cursor = db.viewData();
//        if(cursor.getCount()==0){
//            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
//        }else{
//            while (cursor.moveToNext()){
//                alNews.add(cursor.getString(1));
//            }
//            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alNews);
//            lvNews.setAdapter(adapter);
//        }
//
//    }

}
