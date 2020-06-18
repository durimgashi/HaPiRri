package com.fiek.hapirri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fiek.hapirri.model.Comment;
import com.fiek.hapirri.R;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    Context context;
    ArrayList<Comment> arrayList;

    public CommentAdapter(Context context, ArrayList<Comment> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public CommentAdapter(){

    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_comment_list_view, null);
            TextView t1_id = (TextView) convertView.findViewById(R.id.txt);
            TextView t1_signature = (TextView) convertView.findViewById(R.id.signature_txt);
            TextView t1_comment = (TextView) convertView.findViewById(R.id.comment_txt);

            Comment comment = arrayList.get(position);

            t1_id.setText(comment.getId());
            t1_signature.setText(comment.getSignature());
            t1_comment.setText(comment.getComment());
        }
        return convertView;
    }

}
