package com.danielzou.somarketplace;

/**
 * This represents an inventory item, which has a name, image reference, price, and description.
 */

public class InventoryItem {
    public String name;
    public String imageRef;
    public int price;
    public String description;

    public InventoryItem() {
    }

    public InventoryItem(String name, String imageRef, int price, String description) {
        this.name = name;
        this.imageRef = imageRef;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
