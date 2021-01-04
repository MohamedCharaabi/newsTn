package com.example.news.ui.home;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.MainActivity;
import com.example.news.R;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<String> titles = new ArrayList<>(), descriptions = new ArrayList<>(), images = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerViewView = root.findViewById(R.id.recyclerView);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        recyclerViewView.hasFixedSize();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://www.tunisienumerique.com/feed-actualites-tunisie.xml";

        //request String from URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = convertStringToXMLDocument(response);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("item");

                for (int i=0; i< nList.getLength(); i++){
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE){
                        Element element = (Element) node;
                        String title = element.getElementsByTagName("title").item(0).getTextContent();
                        String description = element.getElementsByTagName("description").item(0).getTextContent();
                        String imageUrl = element.getElementsByTagName("enclosure").item(0).getAttributes().getNamedItem("url").getNodeValue();

                        Log.e("images =>", imageUrl);

                        new Thread(() -> {
                            //Do whatever
                            titles.add(title);
                            descriptions.add(description);
                            images.add(imageUrl);
                        }).start();



                        Adapter adapter = new Adapter(titles, descriptions, images, getActivity());
                        recyclerViewView.setAdapter(adapter);
                        recyclerViewView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }
                }


//                Log.e("Response =>", String.valueOf(titles.size()));
            }

            private void addTitle(String title) {
                titles.add(title);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error>", error.getMessage());
            }
        });

        queue.add(stringRequest);







        return root;
    }


    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            doc.getDocumentElement().normalize();

            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}