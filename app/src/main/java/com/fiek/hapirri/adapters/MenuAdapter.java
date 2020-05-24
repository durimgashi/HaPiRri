package com.fiek.hapirri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fiek.hapirri.R;
import com.fiek.hapirri.model.Item;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    Context context;
    private List<Item> itemList;

    public MenuAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.menuItemName.setText(item.getItemName());
        holder.menuItemType.setText(item.getItemType());
        String price = "$" + item.getPrice();
        holder.menuItemPrice.setText(price);
        Picasso.get().load(item.getImage()).into(holder.menuItemImage);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView menuItemName;
        TextView menuItemType;
        TextView menuItemPrice;
        ImageView menuItemImage;

        private ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            menuItemName = itemView.findViewById(R.id.menuItemName);
            menuItemType = itemView.findViewById(R.id.menuItemType);
            menuItemPrice = itemView.findViewById(R.id.menuItemPrice);
            menuItemImage = itemView.findViewById(R.id.menuItemImage);
        }
    }
}
