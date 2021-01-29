package com.aman.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private Context mcontext;
    private ArrayList<MusicFiles> mfiles;

     public MusicAdapter(Context mcontext, ArrayList<MusicFiles> mfiles){
         this.mcontext=mcontext;
         this.mfiles=mfiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mcontext).inflate(R.layout.new_music_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         holder.file_name.setText(mfiles.get(position).getTitle());

        byte[] image=getAlbumArt(mfiles.get(position).getPath());

        if(image!=null){
            Glide.with(mcontext).asBitmap()
                    .load(image)
                    .into(holder.Album_art);
        }else {
            Glide.with(mcontext)
                    .load(R.drawable.ic_album)
                    .into(holder.Album_art);

        }

        holder.itemView.setOnClickListener(v -> {
            //TODO

//                Intent intent1=new Intent(holder.itemView.getContext(),PlayerActivity2.class);
            Intent intent=new Intent(mcontext,PlayerActivity2.class);
//                Bundle b = new Bundle();
            intent.putExtra("position", position);
            mcontext.startActivity(intent);

        });

    }


    @Override
    public int getItemCount() {
        return mfiles.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
         TextView file_name;
         ImageView Album_art;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            file_name=itemView.findViewById(R.id.music_file_name);
            Album_art=itemView.findViewById(R.id.music_img);
        }


    }


    private  byte[] getAlbumArt(String uri)
    {
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}

