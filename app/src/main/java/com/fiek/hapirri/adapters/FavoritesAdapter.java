package com.fiek.hapirri.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fiek.hapirri.R;
import com.fiek.hapirri.constants.Constants;
import com.fiek.hapirri.model.Restaurant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter  extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<String> favsList;
    private LayoutInflater inflater;

    public FavoritesAdapter(Context context, List<String> favsList) {
        this.inflater = LayoutInflater.from(context);
        this.favsList = favsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_RESTAURANT).document(favsList.get(position));
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Restaurant restaurant;
                if (documentSnapshot.exists()) {
                    restaurant = documentSnapshot.toObject(Restaurant.class);
                    holder.favsName.setText(restaurant.getRestName());
                    Picasso.get().load(restaurant.getImage()).into(holder.favsImage);
                } else {
                    Log.d("FavsError", "No such document");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView favsName;
        ImageView favsImage;

        ViewHolder(View itemView) {
            super(itemView);
            favsName = itemView.findViewById(R.id.favsName);
            favsImage = itemView.findViewById(R.id.favsImage);
        }
    }
}