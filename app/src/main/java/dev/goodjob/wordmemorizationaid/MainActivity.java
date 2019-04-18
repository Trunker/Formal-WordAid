package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission();
        }

//        Button btnTempTest = (Button) findViewById(R.id.btnTempTest);
        Button btnPron = (Button) findViewById(R.id.btnPron);
        Button btnNLE = (Button)findViewById(R.id.btnNLE);
        Button btnNLW = (Button)findViewById(R.id.btnNLW);
//        btnTempTest.setOnClickListener(this);
        btnPron.setOnClickListener(this);
        btnNLE.setOnClickListener(this);
        btnNLW.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
//            case R.id.btnTempTest:
//                intent = new Intent(this, YoutubeActivity.class); //use this to pass the context. btnConvol will start YoutubeActivity
//                break; // YoutubeActivity.class is called a calss literal, which is a way to pass a class as a parameter. class literal + .class acts as the parameter of class

            case R.id.btnPron:
                intent = new Intent(this, StandaloneActivity.class); // btnAdvPro activity will start Standalone activity
                break;
//            case R.id.btnWdEmt:
//                intent = new Intent(this, webview.class);
//                break;
            case R.id.btnNLE:
                intent = new Intent(this, NewsListEditActivity.class);
                break;

            case R.id.btnNLW:
                intent = new Intent(this, WordMemorizeActivity.class);
                break;
            default:
        }
        if(intent != null){
            startActivity(intent);
        }
    }

    public void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }
}
