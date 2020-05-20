package com.fiek.hapirri.adapters;

import android.transition.AutoTransition;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.fiek.hapirri.R;
import com.fiek.hapirri.model.Restaurant;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class RestaurantAdapter extends FirestoreRecyclerAdapter<Restaurant, RestaurantAdapter.RestaurantHolder> {
    private OnItemClickListener listener;

    public RestaurantAdapter(@NonNull FirestoreRecyclerOptions<Restaurant> options) {
        super(options);
    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new RestaurantHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull RestaurantHolder restaurantHolder, int i, @NonNull Restaurant restaurant) {
        restaurantHolder.restHomeName.setText(restaurant.getRestName());
        restaurantHolder.restHomeAddress.setText(restaurant.getAddress());
        Picasso.get().load(restaurant.getImage()).into(restaurantHolder.restHomeImage);
    }

    class RestaurantHolder extends RecyclerView.ViewHolder{
        TextView restHomeName;
        TextView restHomeAddress;
        ImageView restHomeImage;
        LinearLayout expandableLayout;
        CardView homeRestCardView;
        Button restDetailsButton;

        RestaurantHolder(@NonNull View itemView) {
            super(itemView);
            restHomeName = itemView.findViewById(R.id.restHomeName);
            restHomeImage = itemView.findViewById(R.id.restHomeImage);
            restHomeAddress = itemView.findViewById(R.id.restHomeAddress);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            homeRestCardView = itemView.findViewById(R.id.homeRestCardView);
            restDetailsButton = itemView.findViewById(R.id.restDetailsButton);

            homeRestCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Transition transition = new Slide();
                    transition.setDuration(600);
                    transition.addTarget(R.id.expandableLayout);

                    if (expandableLayout.getVisibility() == View.GONE){
                        TransitionManager.beginDelayedTransition(homeRestCardView, new AutoTransition());
                        expandableLayout.setVisibility(View.VISIBLE);
                    } else {
                        TransitionManager.beginDelayedTransition(homeRestCardView, new AutoTransition());
                        expandableLayout.setVisibility(View.INVISIBLE);
                        expandableLayout.setVisibility(View.GONE);
                    }
                }
            });

            restDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
