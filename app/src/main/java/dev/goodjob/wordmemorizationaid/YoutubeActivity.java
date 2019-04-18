package dev.goodjob.wordmemorizationaid;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class YoutubeActivity extends YouTubeBaseActivity //change "extends AppCompatActivity" to "extends YouTubeBaseActivity"
            implements YouTubePlayer.OnInitializedListener, YouTubeThumbnailLoader, YouTubeThumbnailView.OnInitializedListener { // implement an interface "implements YouTubePlayer.OnInitializedListener"
    static final String GOOGLE_API_KEY = "AIzaSyCAlM3MDUwLJDpMB3yqS309p94aKn8UoyU"; //to set the youtube api 3.0 key
    static final String YOUTUBE_Playlist_BASIC = "PLD6B222E02447DC07";
    static final String YOUTUBE_PLAYLIST_ADVANCED = "PLcetZ6gSk96-ayXj5thbTpbh2vHWpP08o";
    static final String YOUTUBE_VIDEO_ID = "fdRmGvmeY1U";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstraintLayout layout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_youtube, null);
        setContentView(layout);
        YouTubePlayerView playerView = new YouTubePlayerView(this);
        playerView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); //set width and height in dp unit
        layout.addView(playerView);
        playerView.initialize(GOOGLE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void setOnThumbnailLoadedListener(OnThumbnailLoadedListener onThumbnailLoadedListener) {

    }

    @Override
    public void setVideo(String s) {

    }

    @Override
    public void setPlaylist(String s) {

    }

    @Override
    public void setPlaylist(String s, int i) {

    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void first() {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public void release() {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        Toast.makeText(this,"Intialized Youtube Player Successfully", Toast.LENGTH_LONG).show();
        youTubePlayer.setPlaybackEventListener(playbackEventListener);//to put the listener to youTubePlayer object
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        if(!wasRestored){
            youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        final int REQUEST_CODE = 1;
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        }else{
            String errorMessage = String.format("This was an errror initializing the YoutubePlayer (%1$s)", youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();//in order to create a toast message, you use the static makeTest() function, "this" parameter indicates the "context"
        }
    }

        private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {
//                Toast.makeText(YoutubeActivity.this, "Good, video is playing ok", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPaused() {
//                Toast.makeText(YoutubeActivity.this, "Good, video is pausing ok", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStopped() {
//                Toast.makeText(YoutubeActivity.this, "Good, video is stopping ok", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBuffering(boolean b) {
                Toast.makeText(YoutubeActivity.this, "Good, video is buffering ok", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSeekTo(int i) {
            }
        };


        private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {
                Toast.makeText(YoutubeActivity.this, "Click Ad now, make the video creator rich", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onVideoStarted() {
                Toast.makeText(YoutubeActivity.this, "Video has started", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onVideoEnded() {
                Toast.makeText(YoutubeActivity.this, "Video has ended", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        };

}
