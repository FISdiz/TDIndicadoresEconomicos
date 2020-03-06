package com.eme.arquitecturaejemplo1.presenter;

import android.util.Log;

import com.eme.arquitecturaejemplo1.api.RequestInterfaceApi;
import com.eme.arquitecturaejemplo1.api.Response;
import com.eme.arquitecturaejemplo1.model.IndicadorEconomico;
import com.eme.arquitecturaejemplo1.util.IndicadorEconomicoHandler;
import com.eme.arquitecturaejemplo1.view.Messenger;
import com.eme.arquitecturaejemplo1.view.MostradorDeValores;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Presentador implements RequestInterfaceApi, IPresentador {

    private static final String TAG = "Presentador";

    private MostradorDeValores mostrador;
    private Messenger messenger;

    public Presentador(MostradorDeValores mostrador, Messenger messenger) {
        this.messenger = messenger;
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
                messenger.showMessage(errorMsg);
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
                        messenger.showMessage("exito");
                    } else {
                        messenger.showMessage("El api no tiene resultados para esta fecha o tipo de moneda");
                        mostrador.limpiarValores();
                    }
                } else {
                    messenger.showMessage("El api no tiene resultados");
                    mostrador.limpiarValores();
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
