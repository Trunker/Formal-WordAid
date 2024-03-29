package dev.goodjob.wordmemorizationaid;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordMemorizeViewPager extends AppCompatActivity {

//    public wordFragmentCollectionAdapter adapterViewPager;

    private ArrayList<Word>words = WordDataProvider.wordList;
    private int numPages = words.size();

    public ViewPager wordMemorizeViewPager;
    public wordFragmentCollectionAdapter adapter;//adpter for hooking Viewpager to Fragment
    private static final String TAG = "WordMemorizeViewPager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_memorize_view_pager);

        wordMemorizeViewPager = findViewById(R.id.wordsViewPager);
        adapter = new wordFragmentCollectionAdapter(getSupportFragmentManager());

        wordMemorizeViewPager.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            int pos = extras.getInt("PARTICULAR_PAGE");
//            int acPos = DatabaseNewsActivity.idArrayList.get(pos);
//            Log.d(TAG, "onCreate: the position of this page is "+ acPos);
            wordMemorizeViewPager.setCurrentItem(pos); // this viewpager use arraylist to populate the fragments but not directly from db
        }
    }

    private class wordFragmentCollectionAdapter extends FragmentStatePagerAdapter{



        public wordFragmentCollectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return WordsMemorizeFragment.newInstance(words.get(i));
        }

        @Override
        public int getCount() {
           return numPages;
        }
    }

}


