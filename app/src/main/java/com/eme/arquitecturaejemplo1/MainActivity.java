package com.eme.arquitecturaejemplo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eme.arquitecturaejemplo1.util.IndicadorEconomicoHandler;

public class MainActivity extends AppCompatActivity implements MostradorDeValores {

    private static String TAG = "MainActivity";
    private TextView resultadoIndicadores;
    private EditText tipoIndicador, fechaIndicador;
    IPresentador presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        presentador = new Presentador(getApplicationContext(), this);
    }

    private void initializeViews(){
        resultadoIndicadores = findViewById(R.id.resultado);
        tipoIndicador = findViewById(R.id.idTipo);
        fechaIndicador = findViewById(R.id.idFecha);
    }

    public void consultarIndicador(View v){
        presentador.consultarIndicador(tipoIndicador.getText().toString(),
                fechaIndicador.getText().toString());
    }

    @Override
    public void mostrarValor(String valor) {
            resultadoIndicadores.setText(valor);
    }
}
