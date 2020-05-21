package com.fiek.hapirri.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fiek.hapirri.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private List<String> gallery;
    private LayoutInflater layoutInflater;
    Context con;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.gallery_item, parent, false);

            ImageView galleryItem = convertView.findViewById(R.id.galleryItem);

            Picasso.get().load(gallery.get(position)).into(galleryItem);
        }
        return convertView;
    }
}
