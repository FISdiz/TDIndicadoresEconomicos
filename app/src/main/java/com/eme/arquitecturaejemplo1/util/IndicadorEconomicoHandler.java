package com.eme.arquitecturaejemplo1.util;

import com.eme.arquitecturaejemplo1.api.RetrofitClient;
import com.eme.arquitecturaejemplo1.api.CallHandle;
import com.eme.arquitecturaejemplo1.api.IndicadorEconomicoApi;
import com.eme.arquitecturaejemplo1.api.RequestInterfaceApi;
import com.eme.arquitecturaejemplo1.api.Response;
import com.eme.arquitecturaejemplo1.model.IndicadorEconomico;

import retrofit2.Call;

public class IndicadorEconomicoHandler extends CallHandle {

    private String tipoIndicador, fechaIndicador;

    public IndicadorEconomicoHandler(String tipoIndicador, String
            fechaIndicador){
        this.tipoIndicador = tipoIndicador;
        this.fechaIndicador = fechaIndicador;
    }

    public void getIndicadorEconomico(RequestInterfaceApi
                                              objRestRequestInterface) {

        Call<IndicadorEconomico> objCall;
        Response objResponse = new Response();
        IndicadorEconomicoApi indicadorEconomicoApiEndPoint;

        objResponse.state = Response.ResponseState.FAILURE;
        objResponse.hasError = true;
        objResponse.requestType = Response.RequestType.SEARCH_FOR_INDICADOR;

        indicadorEconomicoApiEndPoint =
                RetrofitClient.getRetrofitInstance().create(IndicadorEconomicoApi.class);
        objCall =
                indicadorEconomicoApiEndPoint.getIndicadorEconomico(tipoIndicador,
                        fechaIndicador);

        handleCallBack(objRestRequestInterface, objCall, objResponse);
    }
}
