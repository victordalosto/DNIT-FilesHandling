
/**
 * Library that contains functions to handle CSV files
 * @Author: Victor Dalosto
 */

package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class HandlesCSV {

    /**{@code getSNV} Obtem a lista de trechos dentro de um arquivo CSV.
     * @param path (String) Caminho do arquivo CSV contendo o caminho das pastas.
     * @return ArrayList contendo todos os arquivos em String.
     */
    public static ArrayList<String> getPaths(String path) {
        ArrayList<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (isValidString(line))
                    lista.add(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Não foi encontrado o arquivo CSV");
        }
        return lista;
    }




    /** {@code UpdateLOG}. Inserts the 'text' information in the log of the path inserted
     * or create a new one if it doesn't exists.
     * @param text (String): Text that will be updated in the Log
     * @param path (String): Path to a Log.txt File
     */
    public static void updateLog(String text, String path) {
        Path caminhoLog = Paths.get(path);
        try {
            if (!caminhoLog.toFile().isFile()) {
                Files.writeString(Paths.get(path), text, StandardCharsets.UTF_8, StandardOpenOption.CREATE); // Create new file
            } else {
                BufferedReader atualContent = new BufferedReader(new FileReader(path));
                if (isValidString(atualContent.readLine()))
                    text = "\n" + text;
                Files.writeString(Paths.get(path), (text), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                atualContent.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




    /** {@code UpdateLOG}. Inserts the 'text' information in the log of the path inserted
     * or create a new one if it doesn't exists.
     * @param text (String): Text that will be updated in the Log
     * @param path (String): Path to a Log.txt File
     */
    public static void updateLog(List<String> lista, String path) {
        String stringLista = "";
        // Convert the array into a big String
        for (int i = 0; i < lista.size(); i++) { 
            stringLista += lista.get(i) + "\n";
        }
        stringLista = stringLista.replaceAll("\n$", "");
        updateLog(stringLista, path);
    }




    /** {@code clearLOG}. Clear the content of a file.
     * @param path (String): Path to a Log.txt File
     */
    public static void clearLog(String path) {
        try {
            Files.writeString(Paths.get(path), "", 
                    StandardCharsets.UTF_8, 
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




    /** Ordena as linhas de um arquivo em ordem alfabética */
    public static void ordenateLog(String path) {
        ArrayList<String> arrayLine = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path), "UTF-8")) {
            String linha;
            while (scanner.hasNextLine()) {
                linha = scanner.nextLine();
                if (isValidString(linha)) {
                    arrayLine.add(linha);
                }
            }
            Collections.sort(arrayLine);
            clearLog(path);
            updateLog(arrayLine, path);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }




    /** Compara se todos os elementos da list1 estão presentes na list2
     * @param list1 Lista específica que tem alguns dos elementos
     * @param list2 Lista geral com todos os elementos
     * @return HashMap com <ItemList1, Boolean se tem nas duas listas>
     */
    public static HashMap<String, Boolean> compareTwoLists(ArrayList<String> list1, ArrayList<String> list2) {
        HashMap<String, Boolean> listaVerificacao = new HashMap<>();
        list1.forEach(item -> {
            boolean hasCSV = false;
            for (int i = 0; i < list2.size(); i++) {
                if (item.equals(list2.get(i))) {
                    hasCSV = true;
                    break;
                }
            }
            listaVerificacao.put(item, hasCSV);
        });
        return listaVerificacao;
    }




    /** {@code copyFolder}. Walks a folder and copy every file in Sources Fodler to Destiny */
    public static void copyFolder(String src, String dest, boolean overwrite) {
        try {
            Files.walk(Paths.get(src)).forEach(a -> {
                Path b = Paths.get(dest, a.toString().substring(src.length()));
                try {
                    if (!a.toString().equals(src))
                        Files.copy(a, b, overwrite ? new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }
                                : new CopyOption[] {});
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            });
        } catch (IOException e) {
            // permission issue
            e.printStackTrace();
        }
    }




    /** {@code deleteLog}. Delete a file. */
    public static void deleteLog(String path) {
        try {
            Path caminhoLog = Paths.get(path);
            if (caminhoLog.toFile().isFile()) {
                Files.delete(caminhoLog);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




    private static boolean isValidString(String line) {
        return line != null && !line.equals("") && !line.equals(" ") && !line.equals(System.lineSeparator());
    }

}