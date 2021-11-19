package com.example.endoscopycard.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.endoscopycard.R;
import com.example.endoscopycard.Ui.activities.Scan;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Adapter extends RecyclerView.Adapter<Adapter.ContactHolder> {

    public JsonArray contactsList;
    public Context mContext;

    // Counstructor for the Class
    public Adapter(JsonArray contactsList, Context context) {
        this.contactsList = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return contactsList == null ? 0 : contactsList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull final ContactHolder holder, final int position) {

        final JsonObject contact = (JsonObject) contactsList.get(position);
        holder.title.setText(contact.get("scanName").getAsString());

        JsonObject file=contact.get("file").getAsJsonObject();
        JsonObject data=file.get("data").getAsJsonObject();
        JsonArray array =data.get("data").getAsJsonArray();
        byte[] bytes = new byte[array.size()];
        for (int i = 0; i < array.size(); i++) {
            bytes[i]=(byte)((Integer.parseInt(array.get(i).toString())) & 0xFF);
        }
        byte[] decodedString = Base64.decode(Base64.encodeToString(bytes, Base64.DEFAULT), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide.with(mContext).load(decodedByte).into(holder.scan);

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(contact.get("scanTime").getAsString());
        } catch ( ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        holder.date.setText(formatted);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Scan.class);
                intent.putExtra("encodedString",Base64.encodeToString(bytes, Base64.DEFAULT));
                intent.putExtra("scanName",contact.get("scanName").getAsString());
                mContext.startActivity(intent);
            }
        });

    }


    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {
        private ImageView scan;
        private TextView title,date;
        private LinearLayout layout;

        public ContactHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            scan = itemView.findViewById(R.id.scan);
            layout=itemView.findViewById(R.id.layout);
        }

    }
}
