package encryptionapp.encrypt;

import java.util.*;
import java.util.stream.Collectors;


public class PolybiusSquareEncryption {

    private String message;
    private String key;

    public PolybiusSquareEncryption(String message, String key) {
        this.message = message;
        this.key = key;
    }

    private String[][] getSquare() {
        message = message.toUpperCase();
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

    public String encrypt(){
        String[][] square = getSquare();

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

    public String decrypt(){

        String[][] square = getSquare();
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
