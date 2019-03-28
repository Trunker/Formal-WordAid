package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsListItemsWebView extends AppCompatActivity {
    DatabaseNewsActivity db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_items_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView theEconomistPage = (WebView)findViewById(R.id.wvNewslistItemDetail);
        theEconomistPage.getSettings().setJavaScriptEnabled(false);
        theEconomistPage.setWebViewClient(new WebViewClient());
        theEconomistPage.loadUrl(NewsListEditActivity.clickItemUrl);
        db = new DatabaseNewsActivity(this);

        FloatingActionButton fabLookUpWords = findViewById(R.id.fabLookup);
        fabLookUpWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(NewsListItemsWebView.this, DictionaryWebView.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fabAddWords = findViewById(R.id.fabAddWords);
        fabAddWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
