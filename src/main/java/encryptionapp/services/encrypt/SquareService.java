package encryptionapp.services.encrypt;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SquareService {

    public String[][] getSquare(String key) {

        key = key.toUpperCase();
        //pun intr-un vector alfabetul
        ArrayList<String> alphabet = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++)
            if(i != 'J')
                alphabet.add(String.valueOf((char)i));
        // iau literele distincte din cheie
        List<String> distinctKeyLetters = key.chars().mapToObj(e -> Character.toString((char) e))
                .distinct().collect(Collectors.toList());

        //initializez polybiusSquare cu literele distincte ale cheii
        ArrayList<String> polybiusSquare = new ArrayList<>(distinctKeyLetters);
        alphabet.removeAll(distinctKeyLetters); //sterg literele distincte ale cheii din vectorul cu alfabet
        polybiusSquare.addAll(alphabet);  //adaug ce a ramas din alfabet in patratul Polybius

        //convertesc vectorul polybiusSquare intr-o matrice 5 x 5
        int k =0;
        String[][] square = new String[5][5];
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                square[i][j] = polybiusSquare.get(k++);
        return square;
    }
}
