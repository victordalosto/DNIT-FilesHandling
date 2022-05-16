
/**
 * Checks if the Network Folder <Or temporary> (Controle.csv) has indeed a CSV file inside,
 * in compliance with the Edital prerogative.
 * @Author: Victor Dalosto
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import lib.HandlesCSV;


public class FindCSV {
    public static String correctMSG = "";
    public static String wrongMSG = "";

    public static void main(String[] args) {

        String pathToControleCSV = "assets\\Controle.csv";

        String logsPath_Correct = "Logs\\CorrectInput.txt";
        String logsPath_Failed  = "Logs\\NaoTemCSV.txt";

        HandlesCSV.deleteLog(logsPath_Correct);
        HandlesCSV.deleteLog(logsPath_Failed);

        List<String> listSNVPaths = HandlesCSV.getPaths(pathToControleCSV);
        Collections.sort(listSNVPaths);

        ConcurrentHashMap<String, Boolean> mapAllSNV = new ConcurrentHashMap<>();

        listSNVPaths.parallelStream().forEach(path -> {
            boolean hasCSV = false;
            try {
                hasCSV = Files.walk(Paths.get(path)).anyMatch(arquivo -> arquivo.toString().toLowerCase().endsWith(".csv") == true);
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage() + " - " + e.getCause());
            } finally {
                mapAllSNV.put(path, hasCSV);
            }
        });

        // Convert the array into a big String
        mapAllSNV.forEach((key, value) -> {
            if (value == false)
                wrongMSG += key + "\n";
            else
                correctMSG += key + "\n";

        });

        // Removes the last \n of the String and updates the .txt file
        HandlesCSV.updateLog(wrongMSG.replaceAll("\n$", ""), logsPath_Failed);
        HandlesCSV.updateLog(correctMSG.replaceAll("\n$", ""), logsPath_Correct);

        /** Ordena a ordem em que aparece as informações do Log */
        HandlesCSV.ordenateLog(logsPath_Correct);
        HandlesCSV.ordenateLog(logsPath_Failed);

    }
}