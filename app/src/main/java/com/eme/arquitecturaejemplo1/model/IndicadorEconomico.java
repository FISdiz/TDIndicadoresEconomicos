package com.eme.arquitecturaejemplo1.model;

import java.util.List;

public class IndicadorEconomico {

    private String nombre;
    private String unidad_medida;
    private List<SerieIndicadorEconomico> serie;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public List<SerieIndicadorEconomico> getSerie() {
        return serie;
    }

    public void setSerie(List<SerieIndicadorEconomico> serie) {
        this.serie = serie;
    }
}
