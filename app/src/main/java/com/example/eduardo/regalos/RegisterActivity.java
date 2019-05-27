package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    //DECLARAR
    private TextInputEditText email, password, nombres, apellidos;
    private Button registrar;
    //RETROFIT
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //INICIAR API DE RETROFIT
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //VINCULAR
        nombres = findViewById(R.id.nombresRegister);
        apellidos = findViewById(R.id.apellidosRegister);
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passwordRegister);
        registrar = findViewById(R.id.btnRegistrarRegister);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nombres //apellidos //email //password
                // RECUPERO DATOS
                String strEmailR, strnombresR, strApellidosR, strPasswordR;
                strEmailR = email.getText().toString();
                strnombresR = nombres.getText().toString();
                strApellidosR = apellidos.getText().toString();
                strPasswordR = password.getText().toString();
                //ENVIO DATOS
                compositeDisposable.add(myAPI.registerUser(strEmailR, strnombresR, strPasswordR, strApellidosR)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Toast.makeText(getApplicationContext(), " " + s, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })

                );

            }
        });
    }
}

