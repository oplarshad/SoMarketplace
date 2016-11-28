package com.danielzou.somarketplace;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This represents a purchased item, which has an item id, comment, and purchased date.
 */

public class PurchasedItem {
    public String itemId;
    public String comment;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public String dateString;

    public PurchasedItem() {
    }

    public PurchasedItem(String itemId, String comment) {
        this.itemId = itemId;
        this.comment = comment;
        Long time = System.currentTimeMillis();
        this.dateString = dateFormat.format(time);
    }

    public String getItemId() {
        return itemId;
    }

    public String getComment() {
        return comment;
    }

    public String getDateString() {
        return dateString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasedItem that = (PurchasedItem) o;

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        return dateString != null ? dateString.equals(that.dateString) : that.dateString == null;

    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (dateString != null ? dateString.hashCode() : 0);
        return result;
    }
}
