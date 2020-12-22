package com.example.tutorialpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

   /* Version[] versions = {
            new Version("Cupcake", "API 3", R.drawable.cupcake),
            new Version("Donut", "API 4", R.drawable.donut),
            new Version("Eclair", "API 5, 6, 7", R.drawable.eclair),
            new Version("Froyo", "API 8", R.drawable.froyo),
            new Version("Gingerbread", "API 9, 10", R.drawable.gingerbread),
            new Version("Honeycomb", "API 11, 12, 13", R.drawable.honeycomb),
            new Version("Ice Cream Sandwich", "API 14, 15", R.drawable.ics),
            new Version("Jelly Bean", "API 16, 17, 18", R.drawable.jellybean),
            new Version("KitKat", "API 19", R.drawable.kitkat),
            new Version("Lollipop", "API 21, 22", R.drawable.lollipop),
            new Version("Marshmallow", "API 23", R.drawable.marshmallow),
            new Version("Nougat", "API 24, 25", R.drawable.nougat),
            new Version("Oreo", "API 26, 27", R.drawable.oreo)


    }; */

    ArrayList<Version> versions = new ArrayList<Version>();
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter myAdapter = new MyAdapter();
        //myAdapter.addElements(versions);
        recyclerView.setAdapter(myAdapter);

        queue = Volley.newRequestQueue(this);

        JsonArrayRequest request =
                new JsonArrayRequest(Request.Method.GET, " https://raw.githubusercontent.com/kenobicjj/android/main/tutorial4.json ",
        null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        versions.clear();
                        for(int i = 0;i < response.length();i++) {
                            try {   //do try catch incase it doesnt work
                                JSONObject item = response.getJSONObject(i);
                                String name = item.getString("name");
                                String description = item.getString("description");
                                String icon = item.getString("icon");
                                Version version =new Version(name, description, icon);
                                versions.add(version);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myAdapter.addElements(versions);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }});
            queue.add(request);
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        ArrayList<Version> elements = new ArrayList<Version>();
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {       //create viewholder by creating interface
            View rowView = getLayoutInflater().inflate(R.layout.row, parent, false);  // will return a row
            return new MyViewHolder(rowView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {   //get the widgets from interface and bind it to memebr elements

            holder.textView.setText(elements.get(position).getName());   // called from the Version
            holder.textView2.setText(elements.get(position).getDescription());
            //holder.imageView.setImageResource(elements.get(position).getIcon());

            String iconUrl =
                    "https://raw.githubusercontent.com/kenobicjj/android/main/"+elements.get(position).getIcon();
            final LruCache<String, Bitmap> cache =new LruCache<String, Bitmap>(20);
            holder.imageView.setImageUrl(iconUrl, new ImageLoader(queue, new
                    ImageLoader.ImageCache() {
                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }
                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    }));



        }

        @Override
        public int getItemCount() {
            return elements.size();  //should return 13 as got 13 array
        }   //returns the number of items

        public void addElements(ArrayList<Version>versions) {   // put in Version arrays, then add all, and notify
            elements.clear();
            elements.addAll(versions);
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {  //binds widget to textview
            public TextView textView;
            public TextView textView2;
            public NetworkImageView imageView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.versiontitle);
                textView2 = itemView.findViewById(R.id.versionnumber);
                imageView = itemView.findViewById(R.id.icon);

                itemView.setOnClickListener(this);


            }

            @Override
            public void onClick(View v) {   //what happens when clicked

                String name = elements.get(getAdapterPosition()).getName();
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);   //launch new activity main->details
                intent.putExtra("name", name);
                startActivity(intent);



            }
        }
    }
}