package dev.goodjob.wordmemorizationaid;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.DB_TABLE;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ExapleSentence;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.ID;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.PoSProExp;
import static dev.goodjob.wordmemorizationaid.DatabaseNewsActivity.Word;

public class FloatingButtonService extends Service {
    public String createTableSql = "CREATE TABLE IF NOT EXISTS" + "\""+NewsListEditActivity.clickItemUrl + "\"" + "(" +ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + Word + " TEXT,"
            + ExapleSentence +" TEXT, "+ PoSProExp + " TEXT);"; // do not put this string static
    DatabaseNewsActivity db;

    private static final String TAG = "FloatingButtonService";


    private WindowManager mWindowManager;
    private View mFloatingView;
    private View collapsedView;
    private View expandedView;
    public FloatingButtonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floatingwidget, null);

        db = new DatabaseNewsActivity(this);
        dynamicallyCreateTable(db,createTableSql);


        //setting the layout parameters
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager. LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);


        //getting windows services and adding the floating view to it
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);


        //getting the collapsed and expanded view from the floating view
        collapsedView = mFloatingView.findViewById(R.id.layoutCollapsed);
        expandedView = mFloatingView.findViewById(R.id.layoutExpanded);



        Button btn = expandedView.findViewById(R.id.fWBSave);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etFWWord = expandedView.findViewById(R.id.fWWord);
                EditText etFWEx = expandedView.findViewById(R.id.fWExampleSentence);
                EditText etFWPro = expandedView.findViewById(R.id.fWPronuncitionAndExplanation);
                String wordOriginalForm = etFWWord.getText().toString();
                String wordExampleSentence = etFWEx.getText().toString();
                String wordPosExpExplanation = etFWPro.getText().toString();
                Log.d(TAG, "onClick: "+NewsListEditActivity.clickItemUrl);
                if(! wordOriginalForm.equals("")&&!wordExampleSentence.equals("")&&!wordPosExpExplanation.equals("")&&db.insertData(wordOriginalForm,wordExampleSentence,wordPosExpExplanation)){
                    Toast.makeText(FloatingButtonService.this, "Data Successfully saved", Toast.LENGTH_SHORT).show();
                    etFWWord.setText("");
                    etFWEx.setText("");
                    etFWPro.setText("");
//                    wordItemNumer++;
//                    WordDataProvider.addWord(addWordItemActivity.wordItemNumer.toString(),wordOriginalForm, wordExampleSentence, wordPosExpExplanation);
                }else{
                    Toast.makeText(FloatingButtonService.this, "Data unsuccessfully saved, Please retry", Toast.LENGTH_SHORT).show();
                    etFWWord.setText("");
                    etFWEx.setText("");
                    etFWPro.setText("");
                }


            }
        });

        //adding click listener to close button and expanded view
        mFloatingView.findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
        expandedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });


        mFloatingView.findViewById(R.id.relativeLayoutParent).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        //when the drag is ended switching the state of the widget
                        collapsedView.setVisibility(View.GONE);
                        expandedView.setVisibility(View.VISIBLE);
                        return true;


                    case MotionEvent.ACTION_MOVE:
                        //this code is helping the widget to move around the screen with fingers
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    public void  dynamicallyCreateTable(DatabaseNewsActivity db, String sqlInstr){
        SQLiteDatabase dp = db.getWritableDatabase();
        dp.execSQL(sqlInstr);
        dp.close();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}
