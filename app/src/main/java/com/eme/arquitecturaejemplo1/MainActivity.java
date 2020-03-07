package com.eme.arquitecturaejemplo1;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.eme.arquitecturaejemplo1.databinding.ActivityMainBinding;
import com.eme.arquitecturaejemplo1.presenter.IPresentador;
import com.eme.arquitecturaejemplo1.presenter.Presentador;
import com.eme.arquitecturaejemplo1.view.Messenger;
import com.eme.arquitecturaejemplo1.view.MostradorDeValores;

public class MainActivity extends AppCompatActivity implements MostradorDeValores, Messenger {

    private static String TAG = "MainActivity";
    ActivityMainBinding binding;
    IPresentador presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presentador = new Presentador(this, this);
    }

    public void consultarIndicador(View v) {
        presentador.consultarIndicador(this.binding.idTipo.getText().toString(), this.binding.idFecha.getText().toString());
    }

    /**
     * Despliega el valor del indicador económico
     *
     * @param valor del indicador para una fecha en particular
     */
    @Override
    public void mostrarValor(String valor) {
        binding.setValor(valor);
    }

    @Override
    public void limpiarValores() {
        binding.setValor("");
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
