package com.eme.arquitecturaejemplo1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eme.arquitecturaejemplo1.presenter.IPresentador;
import com.eme.arquitecturaejemplo1.presenter.Presentador;
import com.eme.arquitecturaejemplo1.view.Messenger;
import com.eme.arquitecturaejemplo1.view.MostradorDeValores;

public class MainActivity extends AppCompatActivity implements MostradorDeValores, Messenger {

    private static String TAG = "MainActivity";
    private TextView resultadoIndicadores;
    private EditText tipoIndicador, fechaIndicador;
    IPresentador presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        presentador = new Presentador(this, this);
    }

    private void initializeViews() {
        resultadoIndicadores = findViewById(R.id.resultado);
        tipoIndicador = findViewById(R.id.idTipo);
        fechaIndicador = findViewById(R.id.idFecha);
    }

    public void consultarIndicador(View v) {
        presentador.consultarIndicador(tipoIndicador.getText().toString(),
                fechaIndicador.getText().toString());
    }

    @Override
    public void mostrarValor(String valor) {
        resultadoIndicadores.setText(valor);
    }

    @Override
    public void limpiarValores() {
        resultadoIndicadores.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presentador = null;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
