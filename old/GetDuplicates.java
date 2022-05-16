import java.io.FileNotFoundException;
import java.util.ArrayList;

import lib.HandlesCSV;

public class GetDuplicates {

    public static void main(String[] args) throws FileNotFoundException {
        
        String caminhoControle = "assets\\Controle.csv";
        ArrayList<String> listaCaminhos = new ArrayList<>(HandlesCSV.getSNVs(caminhoControle));

        ArrayList<String> novaLista = new ArrayList<>();

        for (int i = 0; i<listaCaminhos.size(); i++){
            if (novaLista.contains(listaCaminhos.get(i))) {
                System.out.println(listaCaminhos.get(i));
            } else {
                novaLista.add(listaCaminhos.get(i));
            }
        }


        System.out.println("Antiga lista: " + listaCaminhos.size());
        System.out.println("Nova lista: " + novaLista.size());
    }
    
}
