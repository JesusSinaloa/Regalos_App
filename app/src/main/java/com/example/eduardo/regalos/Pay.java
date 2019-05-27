package com.example.eduardo.regalos;

import java.io.Serializable;

public class Pay implements Serializable {
    private String IdPay;
    private  String FechaPago;

    public Pay(String idPay, String fechaPago) {
        IdPay = idPay;
        FechaPago = fechaPago;
    }

    public String getIdPay() {
        return IdPay;
    }

    public void setIdPay(String idPay) {
        IdPay = idPay;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String fechaPago) {
        FechaPago = fechaPago;
    }
}
