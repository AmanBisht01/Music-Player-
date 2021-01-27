package com.aman.musicplayer;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
    //what is thread
    private Thread playThread,prevThread,nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        getIntenMethod();
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());

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

    @Override
    protected void onResume() {
        playThreadBtn();
        prevThreadBtn();
        nextThreadBtn();
        super.onResume();
    }

    private void nextThreadBtn() {
        nextThread=new Thread(){
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(v -> nextBtnClicked());
            }
        };

        nextThread.start();
    }

    private void nextBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=(position+1) %listSongs.size();
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaDeta(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mcurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mcurrentPosition);
//                    duration_played.setText(formattedTest(mcurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            play_pause_Btn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();


        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position=(position+1) %listSongs.size();
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaDeta(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mcurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mcurrentPosition);
//                    duration_played.setText(formattedTest(mcurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            play_pause_Btn.setImageResource(R.drawable.ic_play);
        }
    }

    private void prevThreadBtn() {
        prevThread=new Thread(){
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(v -> prevBtnClicked());
            }
        };

        prevThread.start();
    }

    private void prevBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1) <0?listSongs.size() -4: position-1);
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaDeta(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mcurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mcurrentPosition);
//                    duration_played.setText(formattedTest(mcurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            play_pause_Btn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();


        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1) <0?listSongs.size() -4: position-1);
            uri=Uri.parse(listSongs.get(position).getPath());
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            metaDeta(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mcurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mcurrentPosition);
//                    duration_played.setText(formattedTest(mcurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            play_pause_Btn.setImageResource(R.drawable.ic_play);
        }
    }

    private void playThreadBtn() {
        playThread=new Thread(){
            @Override
            public void run() {
                super.run();
                play_pause_Btn.setOnClickListener(v ->
                        playpausedBtnClicked());
            }
        };

        playThread.start();
    }

    private void playpausedBtnClicked() {
    if(mediaPlayer.isPlaying()){
        play_pause_Btn.setImageResource(R.drawable.ic_play);
        mediaPlayer.pause();
        seekBar.setMax(mediaPlayer.getDuration()/1000);
        PlayerActivity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mcurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(mcurrentPosition);
//                    duration_played.setText(formattedTest(mcurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });

    }else{
        play_pause_Btn.setImageResource(R.drawable.ic_pause);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration()/1000);

        PlayerActivity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mcurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(mcurrentPosition);
//                    duration_played.setText(formattedTest(mcurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });

    }
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
        metaDeta(uri);
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

    //what is meta data
    private void metaDeta(Uri uri){
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();

        retriever.setDataSource(uri.toString());
        int durationTotal= Integer.parseInt(listSongs.get(position).getDuration())/1000;
        duration_total.setText(formattedTest(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();

        if(art!=null){
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(cover_art);
        }else{
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.ic_android)
                    .into(cover_art);
        }

    }
}