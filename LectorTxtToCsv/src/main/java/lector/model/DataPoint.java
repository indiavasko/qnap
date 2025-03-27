package lector.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

public class DataPoint {
    private Integer numeroServidores;
    private List<Float> porcentajeOcupacion = new ArrayList<>();
    private List<Float> tiempoRespuesta = new ArrayList<>();

    public DataPoint() {
    }

    public Integer getNumeroServidores() {
        return numeroServidores;
    }

    public void setNumeroServidores(Integer numeroServidores) {
        this.numeroServidores = numeroServidores;
    }

    public List<Float> getPorcentajeOcupacion() {
        return porcentajeOcupacion;
    }

    public void addPorcentajeOcupacion(Float porcentajeOcupacion) {
        this.porcentajeOcupacion.add(porcentajeOcupacion);
    }

    public List<Float> getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public void addTiempoRespuesta(Float tiempoRespuesta) {
        this.tiempoRespuesta.add(tiempoRespuesta);
    }

    public boolean isDataComplete() {
        return nonNull(this.numeroServidores) && nonNull(this.porcentajeOcupacion) && nonNull(this.tiempoRespuesta);
    }

    @Override
    public String toString() {
        Map<Float, Float> data = IntStream.range(0, this.porcentajeOcupacion.size())
                .boxed()
                .collect(Collectors.toMap(
                        this.porcentajeOcupacion::get,
                        this.tiempoRespuesta::get));
        return "DataPoint{" +
                "numeroServidores=" + numeroServidores +
                "\nvalor-key mapa=" + data +
                '}';
    }

    public String toCsvFormat() {
        StringBuilder res = new StringBuilder();
        res.append(this.numeroServidores + ";;\n");

        for(int i = 0; i < this.porcentajeOcupacion.size(); i++){
            res.append(String.format(";%.2f;%f\n", this.porcentajeOcupacion.get(i), this.tiempoRespuesta.get(i)));
        }
        return res.toString();
    }
}
