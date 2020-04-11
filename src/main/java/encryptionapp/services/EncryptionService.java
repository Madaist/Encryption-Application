package encryptionapp.services;

import encryptionapp.encrypt.BifidCipherEncryption;
import encryptionapp.encrypt.PolybiusSquareEncryption;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class EncryptionService implements IEncryptionService {

    @Override
    public File encrypt(Resource resource) {
        File encryptedFile = null;
        try {
            File uploadedFile = resource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(uploadedFile));
            String key = bufferedReader.readLine();
            System.out.println("Key is " + key);
            StringBuilder message = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                message.append(line);
            System.out.println("Initial message is " + message.toString());

            BifidCipherEncryption bifidCipherEncryption = new BifidCipherEncryption(message.toString(), key);
            String encryptedMessage1 = bifidCipherEncryption.encrypt();

            PolybiusSquareEncryption polybiusSquareEncryption = new PolybiusSquareEncryption(encryptedMessage1, key);
            String encryptedMessage = polybiusSquareEncryption.encrypt();

            encryptedFile = new File("encrypted.txt");
            if (encryptedFile.createNewFile()) {
                System.out.println("File created: " + encryptedFile.getName());
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(encryptedFile));
                bufferedWriter.write(encryptedMessage);
            } else {
                System.out.println("File already exists.");
            }
            System.out.println("Mesaj criptat: " + encryptedMessage );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encryptedFile;
    }
}
