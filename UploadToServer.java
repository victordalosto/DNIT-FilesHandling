
/**
 * Script that copy every file in temporaty folder to the correct storage in the network
 * Paths to temp the folder are in 'CorrectInput.txt'
 */

import java.io.IOException;
import java.util.ArrayList;
import lib.HandlesCSV;

public class UploadToServer {
    public static void main(String[] args) throws IOException {

        String pathsCompiled = "Logs\\CorrectInput.txt";

        String baseRede = "\\\\10.100.10.219\\Videos$\\Recebidos\\2021\\Lote2";
        String baseTemp = "C:\\Temp\\CSV-Pavesys";

        ArrayList<String> allSNVPaths = HandlesCSV.getPaths(pathsCompiled);
        allSNVPaths.forEach(path -> {
            String trechoRede = path.replace(baseTemp, baseRede);
            HandlesCSV.copyFolder(path, trechoRede, true);
        });
    }

}
