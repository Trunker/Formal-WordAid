package dev.goodjob.wordmemorizationaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class StandaloneActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_standalone); //to get connected with the layout

        Button btnPlayVideo = (Button)findViewById(R.id.btnPlayVideo);
        Button btnPlayList = (Button)findViewById(R.id.btnPlayList);

        btnPlayVideo.setOnClickListener(this); // pass an instance of current object to implete the interface
        btnPlayList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null; //intent binds activities together
        switch(v.getId()){
            case R.id.btnPlayVideo:
//                intent = YouTubeStandalonePlayer.createVideoIntent(this, YoutubeActivity.GOOGLE_API_KEY, YoutubeActivity.YOUTUBE_VIDEO_ID_BASIC, 0, true, false);
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this,YoutubeActivity.GOOGLE_API_KEY,YoutubeActivity.YOUTUBE_Playlist_BASIC, 0, 0, true, false);
                break; // never leave behind the break clause

            case R.id.btnPlayList:
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this,YoutubeActivity.GOOGLE_API_KEY,YoutubeActivity.YOUTUBE_PLAYLIST_ADVANCED, 0, 0, true, false);
                break;

            default:

        }
        if (intent != null){
            startActivity(intent);
        }

    }
}
