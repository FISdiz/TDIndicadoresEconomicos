package com.eme.arquitecturaejemplo1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.eme.arquitecturaejemplo1.api.RequestInterfaceApi;
import com.eme.arquitecturaejemplo1.api.Response;
import com.eme.arquitecturaejemplo1.model.IndicadorEconomico;
import com.eme.arquitecturaejemplo1.util.IndicadorEconomicoHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Presentador implements RequestInterfaceApi, IPresentador {

    private static final String TAG = "Consumidor";
    private Context context;
    private MostradorDeValores mostrador;

    public Presentador(Context context, MostradorDeValores mostrador) {
        this.context = context;
        this.mostrador = mostrador;
    }

    @Override
    public void response(Response response) {
        try {
            if (response.state == Response.ResponseState.SUCCESS &&
                    !response.hasError) {
                exito(response);
            } else {
                String errorMsg = response.hasError ? response.errorMessage :
                        "No connection error";
                Toast.makeText(context, errorMsg,
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
                        Log.d(TAG, "exito: " + indicadorEconomico.getSerie().get(0).getValor()+
                                     " " + indicadorEconomico.getUnidad_medida());
                        mostrador.mostrarValor(indicadorEconomico.getSerie().get(0)
                                .getValor() +
                                " " + indicadorEconomico.getUnidad_medida());
                        //resultadoIndicadores.setText(indicadorEconomico.getSerie().get(0).getValor()+
                          //      " " + indicadorEconomico.getUnidad_medida());
                    } else {
                        Toast.makeText(context, "El api no tiene resultados para esta fecha o tipo de moneda",
                                Toast.LENGTH_SHORT).show();
                        //resultadoIndicadores.setText("");
                    }
                } else {
                    Toast.makeText(context, "El api no tiene resultados", Toast.LENGTH_SHORT).show();
                    //resultadoIndicadores.setText("");
                }
            }catch (Exception e){
                Log.e(TAG, "Error: "+e);
            }
        }
    }

    public void consultarIndicador(String indicador, String fecha){
        try {
            new IndicadorEconomicoHandler(indicador,
                    fecha)
                    .getIndicadorEconomico(this);
        }catch (Exception e){
            Log.e(TAG, "Error: ", e);
        }
    }
}
