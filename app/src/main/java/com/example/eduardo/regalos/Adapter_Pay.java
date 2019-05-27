package com.example.eduardo.regalos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_Pay extends ArrayAdapter {
    Context contexto;//CONTEXTO DE LA APLICACION
    int iLayout;
    List<Pay> ListPays;//LISTA DE OBJETOS DE LA CLASE PRODUCTO



    //CONTRUCTOR QUE RECIBIRA LOS DATOS DE LA LISTA
    public Adapter_Pay(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        contexto = context;
        iLayout = resource;
        ListPays = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vFila = convertView;
        //DECLARACION DE WIDGETS
        TextView idPay, fechaPago;


        if (vFila == null){//SI NO EXISTE LA FILA HAY QUE CREARLA
            //ESTE OBJETO NOS PERMITE CREAR LA LISTA
            vFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_payments, null, false);

        }
        //VINCULAR WIDGETS
        idPay= vFila.findViewById(R.id.textViewIDPayDetail);
        fechaPago = vFila.findViewById(R.id.textViewFechaPayDetail);





        //LLENAR DATOS
        idPay.setText(ListPays.get(position).getIdPay());
        fechaPago.setText(ListPays.get(position).getFechaPago());


        return vFila;
    }
}
