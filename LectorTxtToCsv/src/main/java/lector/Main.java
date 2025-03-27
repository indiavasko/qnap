package lector;

import lector.model.DataPoint;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class Main {
    private static final String FILENAME = "QNAP\\Programa\\res.txt";
    private static final String PREFIX_SERVER = " Numero de servidores ................................";
    private static final String PREFIX_OCUPANCY = " ocupado ................................";
    private static final String PREFIX_RESPONSE_TIME = " Tiempo medio de respuesta ................................";
    private static List<DataPoint> dataPointList = new ArrayList<>();



    public static void main(String[] args) throws IOException {
        boolean deleteOnExit = false;

        File file = new File(FILENAME);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        DataPoint dp = new DataPoint();
        Status status;
        while (nonNull(line)) {
            dp = extraerDatos(line, dp);
            line = br.readLine();
        }
        StringBuilder sb = new StringBuilder();
        dataPointList.stream()
                        .forEach(dataPoint -> sb.append(dataPoint.toCsvFormat()));

        br.close();
        fr.close();
        if (deleteOnExit) file.delete();

        FileWriter writer = new FileWriter("test.csv");
        writer.write(sb.toString());
        writer.close();
    }

    private static DataPoint extraerDatos(String line, DataPoint dp) {
        String res = "";
        if (line.startsWith(PREFIX_SERVER)) {
            res = line.split(PREFIX_SERVER)[1];
            res = res.replaceAll(" ", "");
            if (nonNull(dp)) {
                dataPointList.add(dp);
            }
            dp = new DataPoint();
            dp.setNumeroServidores(Integer.valueOf(res));
        } else if (line.startsWith(PREFIX_OCUPANCY)) {
            res = line.split(PREFIX_OCUPANCY)[1];
            res = res.replaceAll(" ", "");
            dp.addPorcentajeOcupacion(Float.parseFloat(res));
        } else if (line.startsWith(PREFIX_RESPONSE_TIME)) {
            res = line.split(PREFIX_RESPONSE_TIME)[1];
            res = res.replaceAll(" ", "");
            dp.addTiempoRespuesta(Float.valueOf(res));
        }
        return dp;
    }


    enum Status {
        IGNORE,
        DATA_INCOMPLETE,
        DATA_COMPLETE
    }
}
