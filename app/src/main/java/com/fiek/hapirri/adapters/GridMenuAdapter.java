package com.fiek.hapirri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fiek.hapirri.R;
import com.fiek.hapirri.model.Item;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class GridMenuAdapter extends BaseAdapter implements Filterable {
    private List<Item> itemList;
    public List<Item> itemListFull;
    private Context con;

    public GridMenuAdapter(Context con, List<Item> itemList){
        this.itemList = itemList;
        this.itemListFull = itemList;
        this.con = con;
    }

    @Override
    public int getCount() {
        return itemListFull.size();
    }

    @Override
    public Object getItem(int position) {
        return itemListFull.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(con).inflate(R.layout.menu_item, parent, false);
        ImageView menuItemImage = convertView.findViewById(R.id.menuItemImage);
        TextView menuItemName = convertView.findViewById(R.id.menuItemName);
        TextView menuItemType = convertView.findViewById(R.id.menuItemType);
        TextView menuItemPrice = convertView.findViewById(R.id.menuItemPrice);
        Picasso.get().load(itemListFull.get(position).getImage()).into(menuItemImage);
        menuItemName.setText(itemListFull.get(position).getItemName());
        menuItemType.setText(itemListFull.get(position).getItemType());
        String price = "$" + itemListFull.get(position).getPrice();
        menuItemPrice.setText(price);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = itemList.size();
                    filterResults.values = itemList;
                }else{
                    List<Item> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Item item : itemList){
                        if(item.getItemName().toLowerCase().contains(searchStr)){
                            resultsModel.add(item);
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0){
                    notifyDataSetInvalidated();
                } else {
                    itemListFull = (List<Item>) results.values;
                    Toast.makeText(con, "TEST: " + itemListFull.get(0).getItemName(), Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }
}

