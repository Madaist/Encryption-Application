package encryptionapp.services.encrypt;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PolybiusSquareService {

    private String[][] getSquare(String message, String key) {

        ArrayList<String> alphabet = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++)
            if(i != 'J')
                alphabet.add(String.valueOf((char)i));

        List<String> distinctKeyLetters = key.chars().mapToObj(e -> Character.toString((char) e))
                .distinct().collect(Collectors.toList());

        ArrayList<String> polybiusSquare = new ArrayList<>(distinctKeyLetters);
        alphabet.removeAll(distinctKeyLetters);
        polybiusSquare.addAll(alphabet);

        int k =0;
        String[][] square = new String[5][5];
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                square[i][j] = polybiusSquare.get(k++);
        return square;
    }

    public String encrypt(String message, String key){
        String[][] square = getSquare(message, key);
        message = message.toUpperCase();

        StringBuilder encryptedMessage = new StringBuilder();

        for(int c = 0; c < message.length(); c++)
            for(int i = 0; i < square.length; i++)
                for(int j = 0; j < square[i].length; j++)
                    if(square[i][j].charAt(0) == message.charAt(c)) {
                        encryptedMessage.append(i+1);
                        encryptedMessage.append(j+1);
                    }

        System.out.println("Encrypted message: " + encryptedMessage);
        return encryptedMessage.toString();
    }

    public String decrypt(String message, String key){

        String[][] square = getSquare(message, key);
        StringBuilder decryptedMessage = new StringBuilder();
        ArrayList<Integer> encrypted = new ArrayList<>();

        for(int i = 0; i < message.length(); i++)
            encrypted.add(Integer.parseInt(String.valueOf(message.charAt(i))));

        for(int i = 0; i < encrypted.size()-1; i+= 2)
            decryptedMessage.append(square[encrypted.get(i)-1][encrypted.get(i+1)-1]);

        System.out.println("Decrypted message: " + decryptedMessage);
        return decryptedMessage.toString();
    }

}
