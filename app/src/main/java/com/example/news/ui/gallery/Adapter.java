package com.example.news.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.MainActivity;
import com.example.news.R;
import com.example.news.ui.home.DatabaseHelper;
import com.example.news.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    ArrayList<String> ids, titles, links, descriptions, images;
    Context context;
    public Adapter(ArrayList<String> ids, ArrayList<String> titles, ArrayList<String> links, ArrayList<String> descriptions, ArrayList<String> images, Context context) {
      this.ids = ids;
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

        holder.loveIcon.setImageResource(R.drawable.love_black);
        holder.newTitle.setText(titles.get(position));
        holder.newDescription.setText(descriptions.get(position));

        if (images.get(position).isEmpty()){
            holder.newImage.getResources().getDrawable(R.drawable.girl);
        }else {
            Picasso.get().load(images.get(position)).into(holder.newImage);
        }

//        holder.loveIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DatabaseHelper db  = new DatabaseHelper(context);
//
//
//                boolean insertData =  db.insertData(titles.get(position), descriptions.get(position), images.get(position));
//                if (insertData){
//                    Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(context, "Inserting Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(position)));
                v.getContext().startActivity(i);
//                Toast.makeText(context, "U click" + titles.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                boolean requete = db.deleteData(ids.get(position));
                if (requete) {
                    Toast.makeText(context, "News deleted", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(context, "Deletion Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        holder.cardLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                DatabaseHelper db = new DatabaseHelper(context);
//                boolean requete = db.deleteData(ids.get(position));
//                if (requete) {
//                    Toast.makeText(context, "News deleted", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(context, "Deletion Error", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });



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
