package com.example.eduardo.regalos;

import java.io.Serializable;

public class Producto implements Serializable{
    private int idProducto, cantidad;

    private  String image, nombre, precio, espc2, espc3, espc4, espc5, espc6, espc7, descripcion;

    public Producto(String image, int idProducto, String nombre, String precio, int cantidad, String espc2, String espc3, String espc4, String espc5, String espc6, String espc7, String descripcion) {
        this.image = image;
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.espc2 = espc2;
        this.espc3 = espc3;
        this.espc4 = espc4;
        this.espc5 = espc5;
        this.espc6 = espc6;
        this.espc7 = espc7;
        this.descripcion = descripcion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEspc2() {
        return espc2;
    }

    public void setEspc2(String espc2) {
        this.espc2 = espc2;
    }

    public String getEspc3() {
        return espc3;
    }

    public void setEspc3(String espc3) {
        this.espc3 = espc3;
    }

    public String getEspc4() {
        return espc4;
    }

    public void setEspc4(String espc4) {
        this.espc4 = espc4;
    }

    public String getEspc5() {
        return espc5;
    }

    public void setEspc5(String espc5) {
        this.espc5 = espc5;
    }

    public String getEspc6() {
        return espc6;
    }

    public void setEspc6(String espc6) {
        this.espc6 = espc6;
    }

    public String getEspc7() {
        return espc7;
    }

    public void setEspc7(String espc7) {
        this.espc7 = espc7;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
