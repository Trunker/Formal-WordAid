package dev.goodjob.wordmemorizationaid;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addWordItemActivity extends AppCompatActivity {
//    public static  Integer wordItemNumer = 0;
    DatabaseNewsActivity db;
    Button btnWordlistAddWordsSave;
    EditText tietWordOrigninalForm;
    EditText tietWordExampleSentence;
    EditText tietWordPosProExplanation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_item);

        db = new DatabaseNewsActivity(this);
        tietWordOrigninalForm = findViewById(R.id.tietWordOrigninalForm);
        tietWordExampleSentence = findViewById(R.id.tietWordExampleSentence);
        tietWordPosProExplanation = findViewById(R.id.tietWordPosProExplanation);
        btnWordlistAddWordsSave = findViewById(R.id.btnWordlistAddWordsSave);

        btnWordlistAddWordsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordOriginalForm = tietWordOrigninalForm.getText().toString();
                String wordExampleSentence = tietWordExampleSentence.getText().toString();
                String wordPosExpExplanation = tietWordPosProExplanation.getText().toString();
                if(! wordOriginalForm.equals("")&&!wordExampleSentence.equals("")&&!wordPosExpExplanation.equals("")&&db.insertData(wordOriginalForm,wordExampleSentence,wordPosExpExplanation)){
                    Toast.makeText(addWordItemActivity.this, "Data Successfully saved", Toast.LENGTH_SHORT).show();
                    tietWordOrigninalForm.setText("");
                    tietWordExampleSentence.setText("");
                    tietWordPosProExplanation.setText("");
//                    wordItemNumer++;
//                    WordDataProvider.addWord(addWordItemActivity.wordItemNumer.toString(),wordOriginalForm, wordExampleSentence, wordPosExpExplanation);
                }else{
                    Toast.makeText(addWordItemActivity.this, "Data unsuccessfully saved, Please retry", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}
