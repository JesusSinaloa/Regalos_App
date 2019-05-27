package com.example.eduardo.regalos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.regalos.Paypal.Paypal;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class PayActivity extends AppCompatActivity {
    UserSessionManager session;
    private static final int PAYPAL_REQUEST_CODE = 7171;
    //CREDENCIALES
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Paypal.PAYPAL_CLIENT_ID);
    private TextView txtAmount;
    private Button btnPay;
    Double total;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //COMPROBAR SESSION
        session = new UserSessionManager(getApplicationContext());
        if(!session.checkLogin()) {
            if (Cart.contents().size() > 0){
                    total = Cart.total();
                //Toast.makeText(getApplicationContext(), "Permiso concedido", Toast.LENGTH_SHORT).show();
                //PAYPAL SERVICE
                Intent inServicePaypal = new Intent(this, PayPalService.class);
                inServicePaypal.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                startService(inServicePaypal);

                //VINCULACION
                btnPay = findViewById(R.id.btnPay);
                txtAmount = findViewById(R.id.textViewAmount);
                NumberFormat format = NumberFormat.getCurrencyInstance();
                txtAmount.setText(String.valueOf(format.format(Cart.total())));
                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processPay(Cart.total());
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "Nada que envolver, regresa a los productos", Toast.LENGTH_SHORT).show();
                finish();
                }
        }else{
            Toast.makeText(getApplicationContext(), "Debes iniciar session", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void processPay(Double total) {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(total), "MXN", "PAGO MERCANCIA", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent inPayment = new Intent(this, PaymentActivity.class);
        inPayment.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        inPayment.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(inPayment, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null){
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, PaymentDetailsActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", total.toString())

                        );
                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                if (resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                }else{
                    if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
