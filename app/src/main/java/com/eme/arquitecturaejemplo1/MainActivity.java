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

public class MainActivity extends AppCompatActivity implements
        RequestInterfaceApi {

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
                    fechaIndicador.getText().toString()).getIndicadorEconomico(this);
        }catch (Exception e){
            Log.e(TAG, "Error: ", e);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void response(Response response) {
        try {
            if (response.state == Response.ResponseState.SUCCESS &&
                    !response.hasError) {
                exito(response);
            } else {
                String errorMsg = response.hasError ? response.errorMessage :
                        "No connection error";
                Toast.makeText(getApplicationContext(), errorMsg,
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, errorMsg);
            }
        } catch (Exception objException) {
            Log.e(TAG, objException.getMessage());
            Log.d(TAG, "Response: Exception");
        }


    }

    private void exito(Response response){
        if (response.requestType ==
                Response.RequestType.SEARCH_FOR_INDICADOR) {
            try {
                Gson gson = new GsonBuilder().create();
                String jsonObject = gson.toJson(response.result);
                IndicadorEconomico indicadorEconomico = new
                        Gson().fromJson(jsonObject, IndicadorEconomico.class);
                if (indicadorEconomico.getSerie() != null) {
                    if (indicadorEconomico.getSerie().size() > 0) {

                        resultadoIndicadores.setText(indicadorEconomico.getSerie().get(0).getValor()+
                                " " + indicadorEconomico.getUnidad_medida());
                    } else {
                        Toast.makeText(getApplicationContext(), "El api no tiene resultados para esta fecha o tipo de moneda",
                                Toast.LENGTH_SHORT).show();
                        resultadoIndicadores.setText("");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El api no tiene resultados", Toast.LENGTH_SHORT).show();
                    resultadoIndicadores.setText("");
                }
            }catch (Exception e){
                Log.e(TAG, "Error: "+e);
            }
        }
    }
}
