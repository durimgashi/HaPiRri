package com.fiek.hapirri.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Restaurant implements Parcelable {
    private String restName;
    private String address;
    private List<Item> menu;
    private String image;
    private String description;
    private List<String> gallery;

    public Restaurant() {
    }

    public Restaurant(String restName, String address, List<Item> menu, String image, String description, List<String> gallery) {
        this.restName = restName;
        this.address = address;
        this.description = description;
        this.menu = menu;
        this.image = image;
        this.gallery = gallery;
    }


    protected Restaurant(Parcel in) {
        restName = in.readString();
        address = in.readString();
        menu = in.createTypedArrayList(Item.CREATOR);
        image = in.readString();
        description = in.readString();
        gallery = in.createStringArrayList();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> menu) {
        this.menu = menu;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restName);
        dest.writeString(address);
        dest.writeTypedList(menu);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeStringList(gallery);
    }
}
