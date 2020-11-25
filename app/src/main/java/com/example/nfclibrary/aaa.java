package com.example.nfclibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class aaa extends Activity {
    private ImageView img;
    private TextView bookname;
    private  TextView bookcategory;
    private TextView author1;
    private TextView description1;
    private TextView description2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetails);
        img=findViewById(R.id.image);
        bookname=findViewById(R.id.name);
        bookcategory=findViewById(R.id.category);
        author1=findViewById(R.id.author);
        description1=findViewById(R.id.description1);
        description2=findViewById(R.id.description2);
        getIncomingIntent();
    }
    private void getIncomingIntent(){


        if(getIntent().hasExtra("name") && getIntent().hasExtra("url")&& getIntent().hasExtra("category") && getIntent().hasExtra("author") && getIntent().hasExtra("description")){


            String name = getIntent().getStringExtra("name");
            String category = getIntent().getStringExtra("category");
            String url = getIntent().getStringExtra("url");
            String author = getIntent().getStringExtra("author");
            String description = getIntent().getStringExtra("description");
            bookname.setText("Book : "+""+name);
            bookcategory.setText("Category : "+""+category);
            author1.setText("Author : "+""+author);
            description2.setText(description);
            Picasso.get().load(url).into(img);


        }
    }


}
