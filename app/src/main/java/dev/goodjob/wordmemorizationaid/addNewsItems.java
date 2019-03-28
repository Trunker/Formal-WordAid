package dev.goodjob.wordmemorizationaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addNewsItems extends AppCompatActivity {

    DatabaseHelperActivity db;
    Button btnaddnewssave;
    EditText tiettitle;
    EditText tietdate;
    EditText tieturl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_items);

        db = new DatabaseHelperActivity(this);

        tiettitle = findViewById(R.id.tietTitle);
        tietdate = findViewById(R.id.tietDate);
        tieturl = findViewById(R.id.tietUrl);
        btnaddnewssave = findViewById(R.id.btnWordlistAddWordsSave);

        btnaddnewssave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                   //insert entries into newslist database
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
}
