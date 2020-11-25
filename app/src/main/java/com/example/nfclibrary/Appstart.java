package com.example.nfclibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Appstart extends Activity {
    private ImageView img1;
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.examples);
         img1 = findViewById(R.id.imageView11);
        Button btag = (Button)findViewById(R.id.buttontag);
        btag.setOnClickListener(new OnClickListener(){
            // @Override
            public void onClick(View arg0) {
                startActivity(new Intent(Appstart.this, MainActivity.class));
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}