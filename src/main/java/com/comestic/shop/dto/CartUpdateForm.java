package com.comestic.shop.dto;

import java.util.List;

public class CartUpdateForm {
    private List<CartItemForm> cartItems;

    // Getter và Setter


    public List<CartItemForm> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemForm> cartItems) {
        this.cartItems = cartItems;
    }

    public static class CartItemForm {
        private int cartItemId;
        private int quantity;

        // Getter và Setter


        public int getCartItemId() {
            return cartItemId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setCartItemId(int cartItemId) {
            this.cartItemId = cartItemId;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

