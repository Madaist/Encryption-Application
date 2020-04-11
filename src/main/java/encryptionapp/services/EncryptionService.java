package encryptionapp.services;

import encryptionapp.encrypt.BifidCipherEncryption;
import encryptionapp.encrypt.PolybiusSquareEncryption;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class EncryptionService implements IEncryptionService {

    @Override
    public String encrypt(Resource resource) {
        String encryptedMessage = null;
        try {
            File uploadedFile = resource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(uploadedFile));
            String key = bufferedReader.readLine();
            StringBuilder message = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                message.append(line);

            BifidCipherEncryption bifidCipherEncryption = new BifidCipherEncryption(message.toString(), key);
            String encryptedMessage1 = bifidCipherEncryption.encrypt();

            PolybiusSquareEncryption polybiusSquareEncryption = new PolybiusSquareEncryption(encryptedMessage1, key);
            encryptedMessage = polybiusSquareEncryption.encrypt();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return encryptedMessage;
    }
}
