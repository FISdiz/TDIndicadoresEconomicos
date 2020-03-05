package com.eme.arquitecturaejemplo1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eme.arquitecturaejemplo1.api.RequestInterfaceApi;
import com.eme.arquitecturaejemplo1.api.Response;
import com.eme.arquitecturaejemplo1.model.IndicadorEconomico;
import com.eme.arquitecturaejemplo1.util.IndicadorEconomicoHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements MostradorDeValores {

    private static String TAG = "MainActivity";
    private TextView resultadoIndicadores;
    private EditText tipoIndicador, fechaIndicador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    private void initializeViews(){
        resultadoIndicadores = findViewById(R.id.resultado);
        tipoIndicador = findViewById(R.id.idTipo);
        fechaIndicador = findViewById(R.id.idFecha);
    }

    public void consultarIndicador(View v){
        try {
            new IndicadorEconomicoHandler(tipoIndicador.getText().toString(),
                    fechaIndicador.getText().toString())
                    .getIndicadorEconomico(new Consumidor(getApplicationContext(), this));
        }catch (Exception e){
            Log.e(TAG, "Error: ", e);
        }
    }

    @Override
    public void mostrarValor(String valor) {
            resultadoIndicadores.setText(valor);
    }
}
