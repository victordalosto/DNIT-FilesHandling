
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import lib.HandlesCSV;

public class compareTwoFolders {

    public static void main(String[] args) throws FileNotFoundException {

        String baseRede = "\\\\10.100.10.219\\Videos$\\Recebidos\\2021\\Lote2";
        String baseTemp = "C:\\Temp\\CSV-Pavesys";
        
        ArrayList<String> pathControleTemp = HandlesCSV.getPaths("assets\\Controle.csv");
        ArrayList<String> pathNetworkFiles = HandlesCSV.getPaths("old\\assets\\Controle.csv");

        // Convert temporary name file format to network name file format 
        for (int i=0; i<pathControleTemp.size(); i++) {
            String newPath = pathControleTemp.get(i).replace(baseTemp, baseRede).replace("$", "");
            pathControleTemp.set(i, newPath);
        }

        // Check if all network files are present in pathControleTemp
        ConcurrentHashMap<String, Boolean> mapNetwork = new ConcurrentHashMap<>();
        pathNetworkFiles.parallelStream().forEach(path -> {
            boolean hasCSV = pathControleTemp.contains(path);
            mapNetwork.put(path, hasCSV);
        });


        mapNetwork.forEach((key, value) -> {
            if (value == false)
                System.out.println(key + " - " + value);
        });
    }
    
}
