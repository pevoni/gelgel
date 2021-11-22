package com.example.gelgel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Product extends AppCompatActivity {
    DatabaseReference ref;
    TextView title, description, price, discount;
    ImageView img;
    Button add_to_cart;
public static String productChosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        title = findViewById(R.id.product);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        discount = findViewById(R.id.discount);
        img = findViewById(R.id.imageView);
        add_to_cart = findViewById(R.id.add_to_cart);

        ref = FirebaseDatabase.getInstance().getReference().child("categories").child("c"+ HomePage.index)
                .child("des").child("cat" + CategoryProduct.index).child("products").child("product1");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    ProductModel product = snapshot.getValue(ProductModel.class);
                    title.setText(product.getTitle());
                    description.setText(product.getDescription());
                    price.setText(String.valueOf(product.getPrice()));
                    discount.setText(String.valueOf(product.getDiscount()));


                    Glide.with(img.getContext())
                            .load(product.getImg())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(img);


                }catch (Exception ex){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
add_to_cart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        productChosen = title.getText().toString();
        Intent intent = new Intent(Product.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
});
    }
}