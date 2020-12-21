package com.example.tutorialpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Version[] versions = {
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


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter myAdapter = new MyAdapter();
        myAdapter.addElements(versions);
        recyclerView.setAdapter(myAdapter);

    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        ArrayList<Version> elements = new ArrayList<Version>();
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowView = getLayoutInflater().inflate(R.layout.row, parent, false);  // will return a row
            return new MyViewHolder(rowView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.textView.setText(elements.get(position).getName());   // called from the Version
            holder.textView2.setText(elements.get(position).getDescription());
            holder.imageView.setImageResource(elements.get(position).getIcon());



        }

        @Override
        public int getItemCount() {
            return elements.size();  //should return 13 as got 13 array
        }

        public void addElements(Version[] versions) {   // put in Version arrays, then add all, and notify
            elements.clear();
            elements.addAll(Arrays.asList(versions));
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView textView;
            public TextView textView2;
            public ImageView imageView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.versiontitle);
                textView2 = itemView.findViewById(R.id.versionnumber);
                imageView = itemView.findViewById(R.id.icon);

                itemView.setOnClickListener(this);


            }

            @Override
            public void onClick(View v) {

                String name = elements.get(getAdapterPosition()).getName();
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);



            }
        }
    }
}