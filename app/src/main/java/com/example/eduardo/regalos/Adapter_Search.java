package com.example.eduardo.regalos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Adapter_Search extends ArrayAdapter {
    Context contexto;//CONTEXTO DE LA APLICACION
    int iLayout;
    List<Producto> ListProductos;//LISTA DE OBJETOS DE LA CLASE PRODUCTO
    ArrayList<Producto> arrayList;
    String nombre_imagen = "";
    String URL = "https://tetragex.com/Regalos/";


    //CONTRUCTOR QUE RECIBIRA LOS DATOS DE LA LISTA
    public Adapter_Search(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        contexto = context;
        iLayout = resource;
        ListProductos = objects;
        this.arrayList = new ArrayList<Producto>();
        this.arrayList.addAll(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vFila = convertView;
        //DECLARACION DE WIDGETS
        TextView nombre, precio;
        ImageView imgProducto;

        if (vFila == null){//SI NO EXISTE LA FILA HAY QUE CREARLA
            //ESTE OBJETO NOS PERMITE CREAR LA LISTA
            vFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search, null, false);

        }
        //VINCULAR WIDGETS
        nombre = vFila.findViewById(R.id.textViewNombreADP);
        precio = vFila.findViewById(R.id.textViewPrecioADP);
        imgProducto = vFila.findViewById(R.id.imageViewImagenADP);

        //NOMBRE DE LA IMAGEN PARA PEDIRLA AL SERVIDOR
        nombre_imagen = ListProductos.get(position).getImage().toString();
        final String Uri = URL + nombre_imagen;
        //LENAMOS LA VISTA
        Picasso.with(contexto)//IMAGEN CON PICASSO
                .load(Uri)
                .into(imgProducto);

        //LLENAR DATOS
        nombre.setText(ListProductos.get(position).getNombre());
        precio.setText(ListProductos.get(position).getPrecio());


        return vFila;
    }
    //filter
    public void filter (String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        ListProductos.clear();
        if (charText.length()==0){
            ListProductos.addAll(arrayList);
        }else{
            for (Producto p: arrayList){
                if (p.getNombre().toLowerCase(Locale.getDefault())
                    .contains(charText)){
                    ListProductos.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }
}

