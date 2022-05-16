
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import lib.HandlesCSV;

public class Main {
    public static String correto = "";
    public static String errado = "";

    public static void main(String[] args) {

        String caminhoControle = "assets\\novoControle.csv";
        
        String LogCorrect = "Logs\\CorrectInput.txt";
        String LogFailed = "Logs\\NaoTemCSV.txt";

        HandlesCSV.deleteLog(LogCorrect);
        HandlesCSV.deleteLog(LogFailed);

        List<String> listaCaminho = new ArrayList<>(HandlesCSV.getSNVs(caminhoControle));
        Collections.sort(listaCaminho);
        ConcurrentHashMap<String, Boolean> listaVerificacao = new ConcurrentHashMap<>();

        listaCaminho.parallelStream()
                    .forEach(pasta -> {
                        boolean hasCSV = false;
                        try {
                            hasCSV = Files.walk(Paths.get(pasta)).anyMatch(arquivo ->  arquivo.toString().toLowerCase().endsWith(".csv") == true);
                        } catch (IOException e) {
                            System.out.println("IOException: " + e.getMessage() + " - " + e.getCause());
                        } finally {
                            listaVerificacao.put(pasta, hasCSV);
                        }
        });


        // Convert the array into a big String
        listaVerificacao.forEach((key, value) -> {
            if (value == false) {
                errado += key + "\n";
            } else {
                correto += key + "\n";
            }
        });

        // Removes the last \n of the String and updates the .txt file
        HandlesCSV.updateLog(errado.replaceAll("\n$", ""),  LogFailed);
        HandlesCSV.updateLog(correto.replaceAll("\n$", ""), LogCorrect);

        /** Ordena a ordem em que aparece as informações do Log */
        HandlesCSV.ordenateLog(LogCorrect);
        HandlesCSV.ordenateLog(LogFailed);

    }
}