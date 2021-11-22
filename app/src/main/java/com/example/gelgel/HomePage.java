package com.example.gelgel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmer;
    public static int index = 0;

    private FirebaseRecyclerAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.recyclerViewCategory);
        shimmer = findViewById(R.id.shimmer);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setVisibility(View.INVISIBLE);

        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();

        try {
            setTheAdapter();

        } catch (Exception ex) {
            Toast.makeText(HomePage.this, "Can't Load Data Category", Toast.LENGTH_SHORT).show();
        }
    }



    private void setTheAdapter() {


            FirebaseRecyclerOptions<CategoryModel> options =
                    new FirebaseRecyclerOptions.Builder<CategoryModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("categories"), CategoryModel.class)
                            .build();
            mainAdapter = new FirebaseRecyclerAdapter<CategoryModel, myViewHolderCategory>(options) {

                @NonNull
                @Override
                public myViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
                    return new myViewHolderCategory(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull myViewHolderCategory holder, int position, @NonNull CategoryModel model) {
                    holder.title.setText(model.getTitle());
//                holder.description.setText(model.getDescription());


                    Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                            .setBaseColor(Color.parseColor("#7A7878"))
                            .setBaseAlpha(1)
                            .setHighlightColor(Color.parseColor("#E7E7E7"))
                            .setHighlightAlpha(1)
                            .setDropoff(50)
                            .build();
                    ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
                    shimmerDrawable.setShimmer(shimmer);

                    Glide.with(holder.img.getContext())
                            .load(model.getImg()).
                            placeholder(shimmerDrawable)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(holder.img);

                    if (position == 0) {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                index = recyclerView.getChildLayoutPosition(view) + 1;
                                Log.e("SMTH", index + "");
                                Intent intent = new Intent(view.getContext(), CategoryProduct.class);
                                view.getContext().startActivity(intent);
                            }
                        });
                    }


                }

                // Add this
                @Override
                public void onDataChanged() {
                    if (getItemCount() != 0) {
                        shimmer.setVisibility(View.GONE);
                        shimmer.stopShimmer();
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }

            };


            recyclerView.setAdapter(mainAdapter);

            mainAdapter.startListening();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainAdapter.stopListening();

    }
}

class myViewHolderCategory extends RecyclerView.ViewHolder {
    ImageView img;
    TextView title;

    public myViewHolderCategory(@NonNull View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.imageOfTheCard);
        title = (TextView) itemView.findViewById(R.id.titleOfCard);

    }
}