package com.example.cambiaractividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class instruccionesActivity extends MyApplciation{

    static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);
        super.start(findViewById(R.id.tvInstrucciones));
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnRegresar:
                Intent regresar = new Intent(instruccionesActivity.this,MainActivity.class);
                regresar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                regresar.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                regresar.putExtra("Change",true);
                startActivity(regresar);
                break;
            case R.id.btnSiguiente:
                Intent siguiente = new Intent(instruccionesActivity.this,saludoActivity.class);
                siguiente.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                siguiente.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                siguiente.putExtra("Change",true);
                startActivityForResult(siguiente,3);
                break;
        }
    }

}