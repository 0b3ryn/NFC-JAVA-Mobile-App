package com.example.nfclibrary;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ViewHolder extends RecyclerView.Adapter<ViewHolder.ExampleViewHolder> {
    private ArrayList<books> mExampleList;
    private Context mContext;




    public static class ExampleViewHolder extends RecyclerView.ViewHolder   {

        public ImageView mImageView;
        public TextView mTextView;



        public ExampleViewHolder(final View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.rImageView);
           // mTextView = itemView.findViewById(R.id.rDescriptionTv);







        }



    }



    public ViewHolder(ArrayList<books> examplelist,Context context){
        mExampleList=examplelist;
        mContext=context;



    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);

        ExampleViewHolder evh =  new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, final int position) {
        final books currentItem = mExampleList.get(position);
        Picasso.get().load(currentItem.getUrl()).into(holder.mImageView);
        //holder.mTextView.setText(currentItem.getName());
        final String name = currentItem.getName();
        final String url = currentItem.getUrl();
        final String category = currentItem.getCategory();
        final String author = currentItem.getAuthor();
        final String description = currentItem.getDescription();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // Toast.makeText(mContext,"aaaaa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, aaa.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra("url", url);
               intent.putExtra("category", category);
                intent.putExtra("name", name);
                intent.putExtra("author", author);
                intent.putExtra("description", description);
                mContext.startActivity(intent);

            }
        });






        }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }



}
