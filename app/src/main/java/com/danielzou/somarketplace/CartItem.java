package com.danielzou.somarketplace;

import java.util.ArrayList;
import java.util.Map;

/**
 * This represents a cart item, which has an item id and a comment.
 */

public class CartItem {
    public String itemId;
    public String comment;

    public CartItem() {

    }

    public CartItem(String itemId, String comment) {
        this.itemId = itemId;
        this.comment = comment;
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

        CartItem cartItem = (CartItem) o;

        if (itemId != null ? !itemId.equals(cartItem.itemId) : cartItem.itemId != null)
            return false;
        return comment != null ? comment.equals(cartItem.comment) : cartItem.comment == null;

    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
