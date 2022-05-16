package old;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Rename {
    public static void main(String[] args) {

        String caminho = "assets\\rename.csv";
        String basePath = "C:\\Temp\\CSV-Pavesys\\";

        ArrayList<String> trechosBom = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(caminho))) {
            while (scanner.hasNextLine()) {
                String [] line = scanner.nextLine().split(";");
                line[0] = basePath + line[0];
                line[1] = basePath + line[1];

                try {
                    Files.walk(Paths.get(line[0]));
                    trechosBom.add(line[0]);
                } catch (Exception ex) {
                    System.out.println();
                    ex.printStackTrace();
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        System.out.println();

        trechosBom.forEach(a -> {
            System.out.println(a.replace("C:\\Temp\\CSV-Pavesys\\", ""));
        });

    }
}
