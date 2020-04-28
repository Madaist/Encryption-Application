package encryptionapp.services.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class BifidCipherService {

    private SquareService squareService;

    @Autowired
    public BifidCipherService(SquareService squareService) {
        this.squareService = squareService;
    }

    public String encrypt(String message, String key){
        String[][] square = squareService.getSquare(key); //patratul polybius
        message = message.toUpperCase().replaceAll("[\\n\\t ]", "");

        StringBuilder encryptedMessage = new StringBuilder();
        //linia si coloana fiecarei litere din textul clar vor fi puse pe verticala
        //astfel se vor forma doua linii formate din numere
        ArrayList<Integer> firstRow = new ArrayList<>(); //
        ArrayList<Integer> secondRow = new ArrayList<>();

        //gasim pentru fiecare litera din textul clar linia si coloana corespunzatoare in tabel
        //pe care le stocam in firstRow si secondRow
        for(int c = 0; c < message.length(); c++)
            for(int i = 0; i < square.length; i++)
                for(int j = 0; j < square[i].length; j++)
                    if(square[i][j].charAt(0) == message.charAt(c)) {
                        firstRow.add(i+1);
                        secondRow.add(j+1);
                    }
        //acum trebuie sa mergem pe orizontala si sa luam perechi de cate doua numere
        //pentru a forma literele din textul criptat

        //daca o linie are un numar par de elemente, in textul criptat punem intai
        //toate literele care se formeaza din prima linie si apoi pe toate cele din a doua
        if(firstRow.size() % 2 == 0){
            for(int i = 0; i < firstRow.size()-1; i+= 2)
                encryptedMessage.append(square[firstRow.get(i)-1][firstRow.get(i+1)-1]);
            for(int i = 0; i < secondRow.size()-1; i+= 2)
                encryptedMessage.append(square[secondRow.get(i)-1][secondRow.get(i+1)-1]);
        }
        // //daca o linie are un numar impar de elemente, ultimul numar de pe prima linie va trebui
        //sa se combine cu primul numar de pe a doua linie, pentru a indica pozitia literei in tabel
        else{
            for(int i = 0; i < firstRow.size()-2; i+= 2)
                encryptedMessage.append(square[firstRow.get(i)-1][firstRow.get(i+1)-1]);
            encryptedMessage.append(square[firstRow.get(firstRow.size()-1)][secondRow.get(0)]);
            for(int i = 1; i < secondRow.size()-1; i+= 2)
                encryptedMessage.append(square[secondRow.get(i)-1][secondRow.get(i+1)-1]);
        }

        System.out.println("Encrypted message: " + encryptedMessage);
        return encryptedMessage.toString();
    }



    public String decrypt(String message, String key){
        String[][] square = squareService.getSquare(key);
        message = message.toUpperCase();

        StringBuilder decryptedMessage = new StringBuilder();
        ArrayList<Integer> firstRow = new ArrayList<>();
        ArrayList<Integer> secondRow = new ArrayList<>();

        //pentru fiecare litera din mesajul criptat, vedem linia si coloana pe care se afla
        //si le adaugam in firstRow (prima jumatate) si apoi in secondRow (a doua jumatate)
        if(message.length() % 2 != 0) { //daca mesajul are lungime impara
            int i;
            //prima jumatate
            for (i = 0; i < message.length() / 2; i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[j].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            firstRow.add(j+1);
                            firstRow.add(z+1);
                        }
            }
            // litera din mijloc va avea o cifra corespunzatoare pozitiei pe prima linie
            // si a doua cifra pe a doua linie
            for (int j = 0; j < square.length; j++)
                for (int z = 0; z < square[j].length; z++)
                    if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                        firstRow.add(j);
                        secondRow.add(z);
                    }
            //a doua jumatate
            for (i = message.length() / 2 + 1; i < message.length(); i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[j].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            secondRow.add(j + 1);
                            secondRow.add(z + 1);
                        }
            }
        }
        else { //daca lungimea mesajului este para, nu ne mai punem problema literei din mijloc
            for (int i = 0; i < message.length() / 2; i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[j].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            firstRow.add(j+1);
                            firstRow.add(z+1);
                        }
            }
            for (int i = message.length() / 2; i < message.length(); i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[j].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            secondRow.add(j+1);
                            secondRow.add(z+1);
                        }
            }
        }

        //dupa ce am format cei doi vectori, luam pozitile pe verticala si
        //extragem literele corespunzatoare pentru a forma mesajul descriptat
        for(int i = 0; i < firstRow.size(); i++)
            decryptedMessage.append(square[firstRow.get(i)-1][secondRow.get(i)-1]);

        System.out.println("Decrypted message: " + decryptedMessage);
        return decryptedMessage.toString();
    }
}
