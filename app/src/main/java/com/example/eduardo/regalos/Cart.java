package com.example.eduardo.regalos;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<Item> items = new ArrayList<Item>();

    //METODO PARA AÑADIR AL CARRITO
    public static void insert (Item item){
        items.add(item);
    }
    //UPDATE CANTIDAD DEL CARRITO
    public static void update (Producto product){
        int index = getIndex(product);
        int cantidad = items.get(index).getCantidad() + 1;
        items.get(index).setCantidad(cantidad);
    }
    public static void add (Producto product){
        int index = getIndex(product);
        int cantidad = items.get(index).getCantidad() + 1;
        items.get(index).setCantidad(cantidad);
    }
    public static void rest (Producto product){
        int index = getIndex(product);
        int cantidad = items.get(index).getCantidad();
        if(cantidad <= 1){

        }else{
           cantidad = cantidad - 1;
           items.get(index).setCantidad(cantidad);
        }
    }
    //METODO PARA ELIMINAR
    public static void remove (Producto producto){
        int index = getIndex(producto);
        items.remove(index);
    }

    public static List<Item> contents(){
        return items;
    }

    //TOTAL
    public static double total(){
        double s = 0;
        for (Item item : items){
            s += Double.parseDouble(item.getProduct().getPrecio()) * item.getCantidad();
        }
        return s;
    }
    //SABER SI EL PRODUCTO EXISTE
    public static  boolean isExist(Producto product){
        return getIndex(product) != -1;
    }
    //OBTINE E INDEX EN LA LISTA
    public static int getIndex(Producto product){
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).getProduct().getIdProducto() == product.getIdProducto()){
                return i;
            }
        }
        return -1;
    }

    public static void clean (){
        items.clear();
    }

}
