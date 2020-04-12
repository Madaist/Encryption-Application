package encryptionapp.services.encrypt;

import org.springframework.core.io.Resource;

public interface IEncryptionService {

     String encrypt(Resource resource);
     String decrypt(Resource resource);
}
