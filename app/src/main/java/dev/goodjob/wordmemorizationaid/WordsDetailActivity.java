package dev.goodjob.wordmemorizationaid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class WordsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_detail);
        TextView tvOriginalWord = findViewById(R.id.tvWordDetailOF);
        TextView tvExampleSentence = findViewById(R.id.tvWordDetailExamS);
        TextView tvExplanation = findViewById(R.id.tvWordDetailExp);


        FloatingActionButton fab = findViewById(R.id.fabWordDetail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
