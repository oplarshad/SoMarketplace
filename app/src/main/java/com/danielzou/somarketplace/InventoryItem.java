package com.danielzou.somarketplace;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This represents an inventory item, which has a name, image reference, price, and description.
 */

public class InventoryItem implements Parcelable {
    private String name;
    private String imageRef;
    private int price;
    private String description;

    public InventoryItem() {
    }

    public InventoryItem(String name, String imageRef, int price, String description) {
        this.name = name;
        this.imageRef = imageRef;
        this.price = price;
        this.description = description;
    }

    public InventoryItem(Parcel in) {
        name = in.readString();
        imageRef = in.readString();
        price = in.readInt();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imageRef);
        parcel.writeInt(price);
        parcel.writeString(description);
    }

    public String getName() {
        return name;
    }

    public String getImageRef() {
        return imageRef;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryItem that = (InventoryItem) o;

        if (price != that.price) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (imageRef != null ? !imageRef.equals(that.imageRef) : that.imageRef != null)
            return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (imageRef != null ? imageRef.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + description + Integer.toString(price) + imageRef;
    }

    public static final Creator<InventoryItem> CREATOR = new Creator<InventoryItem>() {
        @Override
        public InventoryItem createFromParcel(Parcel parcel) {
            return new InventoryItem(parcel);
        }

        @Override
        public InventoryItem[] newArray(int size) {
            return new InventoryItem[size];
        }
    };
}
