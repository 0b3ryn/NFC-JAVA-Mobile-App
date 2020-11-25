package com.example.nfclibrary;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.nfc.NdefMessage;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.lang.String;


public class MainActivity extends Activity  {
    private RecyclerView recyclerView;

    DatabaseReference dbArtists;
    NfcAdapter nfcAdapter;
    private ImageButton aaa;

    private ImageView img;
    private TextView err;
    private Button button;
    private   RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aaa);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
    //---------------------------------------------




    }

    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {



            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(parcelables != null && parcelables.length > 0)
            {
                readTextFromMessage((NdefMessage) parcelables[0]);
            }else{
                Toast.makeText(this, "No NDEF messages found!", Toast.LENGTH_SHORT).show();
            }



        }
    }

    private void readTextFromMessage(NdefMessage ndefMessage) {

        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length>0){

            NdefRecord ndefRecord = ndefRecords[0];

            final String tagContent = getTextFromNdefRecord(ndefRecord);
            //Toast.makeText(this, tagContent, Toast.LENGTH_SHORT).show();

            final String[] cat = new String[1];
            img=findViewById(R.id.image);
            err = findViewById(R.id.text_view_name);
            button= findViewById(R.id.button);
            dbArtists = FirebaseDatabase.getInstance().getReference().child("books");
            dbArtists.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String name = snapshot.child("name").getValue().toString();

                            if(name.equals(tagContent)) {
                                String url = snapshot.child("url").getValue().toString();
                                 cat[0] =snapshot.child("category").getValue().toString();
                                err.setText(name);
                                Picasso.get().load(url).into(img);
                                button.setVisibility(View.VISIBLE);

                            }

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }





            });
            mLayoutManager = new LinearLayoutManager(this);
            button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    setContentView(R.layout.activity_posts);
                    mRecyclerView = findViewById(R.id.recyclerView);

                    mRecyclerView.setHasFixedSize(true);
                   // Toast.makeText(getBaseContext(),cat[0],Toast.LENGTH_LONG).show();


                    final ArrayList<books> book = new ArrayList<>();
                    dbArtists =  FirebaseDatabase.getInstance().getReference().child("books");
                    dbArtists.addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    String category = snapshot.child("category").getValue().toString();
                                    String name = snapshot.child("name").getValue().toString();
                                    if(category.equals(cat[0]) & !(name.equals(tagContent))) {

                                        String url = snapshot.child("url").getValue().toString();
                                        String author = snapshot.child("author").getValue().toString();
                                        String description = snapshot.child("description").getValue().toString();

                                        book.add(new books(category, name, url,author,description));
                                    }



                                }
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mAdapter = new ViewHolder(book,getBaseContext());

                                mRecyclerView.setAdapter(mAdapter);




                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }





                    });




                }
            });






        }else
        {
            Toast.makeText(this, "No NDEF records found!", Toast.LENGTH_SHORT).show();
        }

    }
    private void enableForegroundDispatchSystem() {

        Intent intent = new Intent(this,MainActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }



    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;

        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;

            tagContent = new String(payload, languageSize-1,
                    payload.length, textEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return tagContent;
    }




}