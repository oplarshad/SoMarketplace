package com.danielzou.somarketplace;

import java.util.Date;

/**
 * This represents a purchased item, which has an item id, comment, and purchased date.
 */

public class PurchasedItem {
    public String itemId;
    public String comment;
    public Date date;

    public PurchasedItem() {
    }

    public PurchasedItem(String itemId, String comment, Date date) {
        this.itemId = itemId;
        this.comment = comment;
        this.date = date;
    }

    public String getItemId() {
        return itemId;
    }

    public String getComment() {
        return comment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasedItem that = (PurchasedItem) o;

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
