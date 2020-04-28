package encryptionapp.services.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PolybiusSquareService {

    private SquareService squareService;

    @Autowired
    public PolybiusSquareService(SquareService squareService) {
        this.squareService = squareService;
    }

    public String encrypt(String message, String key){
        String[][] square = squareService.getSquare(key);
        message = message.toUpperCase();

        StringBuilder encryptedMessage = new StringBuilder();

        //pentru fiecare litera din mesajul clar, ii caut pozitia in patratul Poybius
        //si o adaug la mesajul criptat
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

        message = message.replaceAll("[\\n\\t ]", "");
        String[][] square = squareService.getSquare(key);
        StringBuilder decryptedMessage = new StringBuilder();
        ArrayList<Integer> encrypted = new ArrayList<>();

        // convertesc cifrele din mesajul criptat(primit ca string) in numere
        for(int i = 0; i < message.length(); i++)
            encrypted.add(Integer.parseInt(String.valueOf(message.charAt(i))));

        //iau perechi de doua cate doua cifre si vad ce litera se gaseste la pozitia respectiva
        //litera o adaug la mesajul criptat
        for(int i = 0; i < encrypted.size()-1; i+= 2)
            decryptedMessage.append(square[encrypted.get(i)-1][encrypted.get(i+1)-1]);

        System.out.println("Decrypted message: " + decryptedMessage);
        return decryptedMessage.toString();
    }
}
