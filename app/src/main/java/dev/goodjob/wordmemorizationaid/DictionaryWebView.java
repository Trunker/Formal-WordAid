package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DictionaryWebView extends AppCompatActivity {
    public static final String cambridegeDic = "https://dictionary.cambridge.org/dictionary/english/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_web_view);
        WebView wvDicLookUp = (WebView) findViewById(R.id.wvDicLookUp);
        wvDicLookUp.getSettings().setJavaScriptEnabled(true);
        wvDicLookUp.setWebViewClient(new WebViewClient());
        wvDicLookUp.loadUrl(cambridegeDic);


        final FloatingActionButton fabAddWords = findViewById(R.id.fabAddWords);
        fabAddWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(DictionaryWebView.this, FloatingButtonService.class));

//                    finish();
            }
        });


    }
}
