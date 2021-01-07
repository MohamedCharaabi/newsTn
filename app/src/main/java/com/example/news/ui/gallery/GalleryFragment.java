package com.example.news.ui.gallery;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.ui.home.DatabaseHelper;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    DatabaseHelper myDb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.database_recycler);

        myDb = new DatabaseHelper(getActivity());
        ArrayList<String> ids= new ArrayList<>();
        ArrayList<String> titles= new ArrayList<>();
        ArrayList<String> links= new ArrayList<>();
        ArrayList<String> descriptions= new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();
        Cursor cr = myDb.getAll();
        StringBuffer buffer = new StringBuffer();
         if (cr.getCount() == 0) {
             Toast.makeText(getActivity(), "No Saved News", Toast.LENGTH_SHORT).show();
         }
         while ( cr.moveToNext()){
             ids.add(cr.getString(0));
            titles.add(cr.getString(1));
            descriptions.add(cr.getString(2));
            images.add(cr.getString(3));
            links.add(cr.getString(4));
         }

            Adapter adapter = new Adapter(ids, titles, links, descriptions, images, getActivity());
         recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }
}