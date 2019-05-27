package com.example.eduardo.regalos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {


    //DECLARACION DE VARIABLES Y OBJETOS Y WIDGETS
    private String TAG = "Principal";
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //VINCULACION DE WIDGETS
        /*BARRA DE NAVEGACION*/
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        /*EVENTO ANONIMO*/
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        /*INICIAMOS CON EL FRAGMENT HOME POR DEFECTO*/
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new Home()).commit();



    }

    //EVENTO PARA BOTONES DE LA BARRA DE NAVEGACION INFERIOR
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null; //DECLARAMOS FRAGMENTO
            switch (menuItem.getItemId()){//CUANDO DA CLICK SE VERIFICA EL ID
                case R.id.buttom_home:
                    selectedFragment = new Home();//SE INTANCIA FRAGMENT
                    break;
                case R.id.buttom_search:
                    selectedFragment = new Search();
                    break;
                case R.id.buttom_shipp:
                    selectedFragment = new Envios();
                    break;
                case R.id.buttom_acount:
                    selectedFragment = new Perfil();
                    break;
            }
            //SE CONSTRUYE EL FRAGMENTO
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor, selectedFragment).commit();
            return true;

        }
    };


}
