package com.aman.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.aman.musicplayer.MainActivity.musicFiles;

public class PlayerActivity2 extends AppCompatActivity {
    TextView song_name,artist_name,duration_played,duration_total;
    ImageView cover_art,nextBtn,prevBtn,backBtn,shuffleBtn,repeatBtn;
    SeekBar seekBar;
    FloatingActionButton play_pause_Btn;
    int position=-1;
    ArrayList<MusicFiles> listSongs=new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private final Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        getIntenMethod();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mcurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(mcurrentPosition);
                    duration_played.setText(formattedTest(mcurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });


    }

    private String formattedTest(int mcurrentPosition) {
    String totalout;
    String totalNew;
    String Second=String.valueOf(mcurrentPosition%60);
    String Minutes=String.valueOf(mcurrentPosition/60);
    totalout=Minutes+":"+Second;
    totalNew=Minutes +":"+"0"+Second;
    if(Second.length()==1)
    {
        return totalNew;
    }else
    {
        return totalout;
    }

    }

    private void getIntenMethod() {
        position=getIntent().getIntExtra("position",-1);
        listSongs=musicFiles;
        if(listSongs !=null){
            play_pause_Btn.setImageResource(R.drawable.ic_pause);
            uri=Uri.parse(listSongs.get(position).getPath());
        }
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration()/1000);
    }

    private void initView() {
    song_name=findViewById(R.id.song_name);
    artist_name=findViewById(R.id.song_artist);
    duration_played=findViewById(R.id.duration_played);
    duration_total=findViewById(R.id.duration_total);
    cover_art=findViewById(R.id.cover_art);
    nextBtn=findViewById(R.id.id_next);
    prevBtn=findViewById(R.id.id_prev);
    backBtn=findViewById(R.id.back_btn);
    shuffleBtn=findViewById(R.id.id_shuffle);
    repeatBtn=findViewById(R.id.id_repeat);
    play_pause_Btn=findViewById(R.id.play_pause);
    seekBar=findViewById(R.id.seek_bar);


    }
}