package com.danielzou.somarketplace;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This represents a purchased item, which has an item id, comment, and purchased date.
 */

class PurchasedItem {
    private String itemId;
    private String comment;
    private String dateString;
    private String displayName;

    public PurchasedItem() {
    }

    public PurchasedItem(String itemId, String comment, String displayName) {
        this.itemId = itemId;
        this.comment = comment;
        Long time = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.dateString = dateFormat.format(time);
        this.displayName = displayName;
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

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasedItem that = (PurchasedItem) o;

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (dateString != null ? !dateString.equals(that.dateString) : that.dateString != null)
            return false;
        return displayName != null ? displayName.equals(that.displayName) : that.displayName == null;

    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (dateString != null ? dateString.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }
}
