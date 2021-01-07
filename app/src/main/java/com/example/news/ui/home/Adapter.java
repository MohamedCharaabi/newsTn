package com.example.news.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.ui.gallery.GalleryFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<String> titles, links, descriptions, images;
    Context context;

    public Adapter(ArrayList<String> titles, ArrayList<String> links, ArrayList<String> descriptions, ArrayList<String> images, Context context) {
        this.titles = titles;
        this.links = links;
        this.descriptions = descriptions;
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_news,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.newTitle.setText(titles.get(position));
        holder.newDescription.setText(descriptions.get(position));

        if (images.get(position).isEmpty()){
            holder.newImage.getResources().getDrawable(R.drawable.girl);
        }else {
            Picasso.get().load(images.get(position)).into(holder.newImage);
        }

        holder.loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper db  = new DatabaseHelper(context);

                Log.e("title =>", titles.get(position));
                Log.e("link =>", links.get(position));
                Log.e("description=>", descriptions.get(position));
                Log.e("image =>", images.get(position));
               boolean insertData =  db.insertData(titles.get(position), links.get(position), descriptions.get(position), images.get(position));
               if (insertData){
                   Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(context, "Inserting Error", Toast.LENGTH_SHORT).show();
               }
            }
        });

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(position)));
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView newImage;
        TextView newTitle, newDescription;
        ConstraintLayout cardLayout;
        ImageButton loveIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImage = itemView.findViewById(R.id.cardImage);
            newTitle = itemView.findViewById(R.id.cardTitle);
            newDescription = itemView.findViewById(R.id.cardDescription);
            cardLayout = itemView.findViewById(R.id.cardLayout);
            loveIcon = itemView.findViewById(R.id.love_icon);

        }
    }


}
