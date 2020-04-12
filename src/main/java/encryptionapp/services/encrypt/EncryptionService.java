package encryptionapp.services.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class EncryptionService implements IEncryptionService {

    private  BifidCipherService bifidCipherService;
    private PolybiusSquareService polybiusSquareService;

    @Autowired
    public EncryptionService(BifidCipherService bifidCipherService, PolybiusSquareService polybiusSquareService) {
        this.bifidCipherService = bifidCipherService;
        this.polybiusSquareService = polybiusSquareService;
    }

    @Override
    public String encrypt(Resource resource) {
        String encryptedMessage = null;
        try {
            File uploadedFile = resource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(uploadedFile));
            String key1 = bufferedReader.readLine();
            String key2 = bufferedReader.readLine();
            StringBuilder message = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                message.append(line);

            String encryptedMessage1 = bifidCipherService.encrypt(message.toString(), key1);
            encryptedMessage = polybiusSquareService.encrypt(encryptedMessage1, key2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return encryptedMessage;
    }

    @Override
    public String decrypt(Resource resource) {
        String decryptedMessage = null;
        try {
            File uploadedFile = resource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(uploadedFile));
            String key1 = bufferedReader.readLine();
            String key2 = bufferedReader.readLine();
            StringBuilder message = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                message.append(line);

            String decryptedMessage1 = polybiusSquareService.decrypt(message.toString(), key1);
            decryptedMessage = bifidCipherService.decrypt(decryptedMessage1, key2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return decryptedMessage;
    }
}
