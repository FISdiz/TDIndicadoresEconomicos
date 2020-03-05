package com.eme.arquitecturaejemplo1;

import android.content.Context;

public class PresentadorM implements IPresentador {

    private Context context;
    private MostradorDeValores mostrador;

    public PresentadorM(Context context, MostradorDeValores mostrador) {
        this.context = context;
        this.mostrador = mostrador;
    }

    @Override
    public void consultarIndicador(String indicador, String fecha) {
        mostrador.mostrarValor("10");
    }
}
