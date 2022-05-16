
/**
 * Script that copy every file in temp file to the correct storage in network
 * Paths to temp folder are in CorrectInput.txt
 */

import java.io.IOException;
import java.util.ArrayList;
import lib.HandlesCSV;

public class UploadToServer {
    public static void main(String[] args) throws IOException {

        String caminhoControle = "Logs\\CorrectInput.txt";
        String baseRede = "\\\\10.100.10.219\\Videos$\\Recebidos\\2021\\Lote2";
        String baseTemp = "C:\\Temp\\CSV-Pavesys";

        ArrayList<String> listaCaminho = new ArrayList<>(HandlesCSV.getSNVs(caminhoControle));
        listaCaminho.forEach(trecho -> {
            String trechoRede = trecho.replace(baseTemp, baseRede);
            HandlesCSV.copyFolder(trecho, trechoRede, true);
        });
    }

}
