package com.example.eduardo.regalos;

import com.example.eduardo.regalos.Producto;

import java.io.Serializable;

public class Item implements Serializable {
    private Producto product;
    private int cantidad;

    public Item(Producto objetoProducto, int cantidad) {
        this.product = objetoProducto;
        this.cantidad = cantidad;
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
