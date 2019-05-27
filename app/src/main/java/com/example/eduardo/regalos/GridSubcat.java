package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GridSubcat extends AppCompatActivity {
    private ImageButton btnZapatos, btnLibros, btnBelleza, btnModa, btnVideojuegos, btnFotografia, btnCelulares, btnComputadoras, btnRopa, btnOtros;
    private Intent inVerTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_subcat);
        //VINCULACION
        btnZapatos = findViewById(R.id.btnZapatos);
        btnLibros = findViewById(R.id.btnLibros);
        btnBelleza = findViewById(R.id.btnBelleza);
        btnModa = findViewById(R.id.btnModa);
        btnVideojuegos = findViewById(R.id.btnVideojuegos);
        btnFotografia = findViewById(R.id.btnFotografias);
        btnCelulares = findViewById(R.id.btnCelulares);
        btnComputadoras = findViewById(R.id.btnComputadoras);
        btnRopa = findViewById(R.id.btnRopa);
        btnOtros = findViewById(R.id.btnOtros);


        btnZapatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Zapatos");
                startActivity(inVerTodos);
            }
        });
        btnLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Libros");
                startActivity(inVerTodos);
            }
        });

        btnBelleza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Belleza");
                startActivity(inVerTodos);
            }
        });
        btnModa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Moda");
                startActivity(inVerTodos);
            }
        });
        btnFotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Fotografia");
                startActivity(inVerTodos);
            }
        });
        btnCelulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Celulares");
                startActivity(inVerTodos);
            }
        });
        btnComputadoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Computadoras");
                startActivity(inVerTodos);
            }
        });
        btnRopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Ropa");
                startActivity(inVerTodos);
            }
        });
        btnVideojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inVerTodos =  new Intent(GridSubcat.this, Ver_Todos.class);
                inVerTodos.putExtra("type", "Videojuegos");
                startActivity(inVerTodos);
            }
        });
        btnOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
