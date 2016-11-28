package com.danielzou.somarketplace;

/**
 * This represents an inventory item, which has a name, image reference, price, and description.
 */

public class InventoryItem {
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
}
