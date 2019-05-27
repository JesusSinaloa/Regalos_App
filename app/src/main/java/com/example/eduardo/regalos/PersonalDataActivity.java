package com.example.eduardo.regalos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eduardo.regalos.Retrofit.INodeJS;
import com.example.eduardo.regalos.Retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PersonalDataActivity extends AppCompatActivity {
    private TextInputEditText email, nombres, apellidos;
    private Button update;
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
        setContentView(R.layout.activity_personal_data);
        //VINCULACION
        email = findViewById(R.id.emailUpdate);
        nombres = findViewById(R.id.nombresUpdate);
        apellidos = findViewById(R.id.apellidosUpdate);
        update = findViewById(R.id.btnUpdate);

        String strNombre = getIntent().getStringExtra("nombre");
        String strApellido = getIntent().getStringExtra("apellido");
        final String strEmail = getIntent().getStringExtra("email");

        email.setText(strEmail);
        nombres.setText(strNombre);
        apellidos.setText(strApellido);

        //INICIAR API DE RETROFIT
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Actualizando...", Toast.LENGTH_SHORT).show();
                updateData(email.getText().toString(), nombres.getText().toString(), apellidos.getText().toString(), strEmail);
            }
        });




    }
    private void updateData(String email, String nombres, String apellidos, String id){
        compositeDisposable.add(myAPI.updateUserData(email, nombres, apellidos, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(getApplicationContext(), " " + s, Toast.LENGTH_SHORT).show();
                        UserSessionManager session;
                        session = new UserSessionManager(getApplicationContext());
                        session.logoutUser();
                        Toast.makeText(getApplicationContext(), "Tendra que iniciar session de nuevo, por integridad de los datos" , Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })

        );
    }
}
