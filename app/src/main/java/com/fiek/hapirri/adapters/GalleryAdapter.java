package com.fiek.hapirri.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.fiek.hapirri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private List<String> gallery;
    private LayoutInflater layoutInflater;
    private Context con;

    public GalleryAdapter(Context con, List<String> gallery){
        this.con = con;
        this.layoutInflater = LayoutInflater.from(con);
        this.gallery = gallery;
    }

    @Override
    public int getCount() {
        return gallery.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.gallery_item, parent, false);
            ImageView galleryItem = convertView.findViewById(R.id.galleryItem);
            Picasso.get().load(gallery.get(position)).into(galleryItem);

            galleryItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    showPopupImage(position);
                }
            });
        }
        return convertView;
    }
    private void showPopupImage(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        View popupView = LayoutInflater.from(con).inflate(R.layout.image_popup, null);
        ImageView popupImage = popupView.findViewById(R.id.popupImage);
        Picasso.get().load(gallery.get(position)).into(popupImage);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
