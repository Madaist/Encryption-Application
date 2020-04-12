package encryptionapp.services.encrypt;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BifidCipherService {

    public String encrypt(String message, String key){
        String[][] square = getSquare(key);
        message = message.toUpperCase().replaceAll("[\\n\\t ]", "");

        StringBuilder encryptedMessage = new StringBuilder();
        ArrayList<Integer> firstRow = new ArrayList<>();
        ArrayList<Integer> secondRow = new ArrayList<>();

        for(int c = 0; c < message.length(); c++)
            for(int i = 0; i < square.length; i++)
                for(int j = 0; j < square[i].length; j++)
                    if(square[i][j].charAt(0) == message.charAt(c)) {
                        firstRow.add(i+1);
                        secondRow.add(j+1);
                    }
        if(firstRow.size() % 2 == 0){
            for(int i = 0; i < firstRow.size()-1; i+= 2)
                encryptedMessage.append(square[firstRow.get(i)-1][firstRow.get(i+1)-1]);
            for(int i = 0; i < secondRow.size()-1; i+= 2)
                encryptedMessage.append(square[secondRow.get(i)-1][secondRow.get(i+1)-1]);
        }
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
        String[][] square = getSquare(key);

        StringBuilder decryptedMessage = new StringBuilder();
        ArrayList<Integer> firstRow = new ArrayList<>();
        ArrayList<Integer> secondRow = new ArrayList<>();

        if(message.length() % 2 != 0) {
            int i;
            for (i = 0; i < message.length() / 2; i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[i].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            firstRow.add(j+1);
                            firstRow.add(z+1);
                        }
            }
            // litera din mijloc, care va avea o cifra pe prima linie si a doua cifra pe a doua linie
            for (int j = 0; j < square.length; j++)
                for (int z = 0; z < square[i].length; z++)
                    if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                        firstRow.add(j);
                        secondRow.add(z);
                    }

            for (i = message.length() / 2 + 1; i < message.length(); i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[j].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            secondRow.add(j + 1);
                            secondRow.add(z + 1);
                        }
            }
        }
        else {
            for (int i = 0; i < message.length() / 2; i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[i].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            firstRow.add(j+1);
                            firstRow.add(z+1);
                        }
            }
            for (int i = message.length() / 2; i < message.length(); i++) {
                for (int j = 0; j < square.length; j++)
                    for (int z = 0; z < square[i].length; z++)
                        if (square[j][z].equals(String.valueOf(message.charAt(i)))) {
                            secondRow.add(j+1);
                            secondRow.add(z+1);
                        }
            }
        }

        for(int i = 0; i < firstRow.size(); i++)
            decryptedMessage.append(square[firstRow.get(i)-1][secondRow.get(i)-1]);

        System.out.println("Decrypted message: " + decryptedMessage);
        return decryptedMessage.toString();
    }

    private String[][] getSquare(String key) {

        key = key.toUpperCase();
        ArrayList<String> alphabet = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++)
            if(i != 'J')
                alphabet.add(String.valueOf((char)i));

        List<String> distinctKeyLetters = key.chars().mapToObj(e -> Character.toString((char) e))
                .distinct().collect(Collectors.toList());

        ArrayList<String> matrix = new ArrayList<>(distinctKeyLetters);
        alphabet.removeAll(distinctKeyLetters);
        matrix.addAll(alphabet);

        int k =0;
        String[][] square = new String[5][5];
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                square[i][j] = matrix.get(k++);
        return square;
    }

}
