package com.example.eduardo.regalos;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Envios extends Fragment {
    private ListView lstViewCart;
    private FloatingActionButton btnPay;

    public Envios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_envios, container, false);
        //VINCULACION
        lstViewCart = rootView.findViewById(R.id.ListViewCartFragment);
        btnPay = rootView.findViewById(R.id.floatingActionButtonPayFragment);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inPay = new Intent(getActivity().getApplicationContext(), PayActivity.class);
                startActivity(inPay);
            }
        });

        //CARGAR ADAPTADOR
        cargarAdaptador();

        return rootView;

    }
    private void cargarAdaptador() {
        Adapter_Cart miAdaptador = new Adapter_Cart(getActivity().getApplicationContext(),R.layout.item_cart,Cart.contents());//LE MANDAMOS AL CONTRUCTOR DE LA CLASE EL CONTEXTO; LA LISTA Y ILAYOUT
        lstViewCart.setAdapter(miAdaptador);
    }

}
