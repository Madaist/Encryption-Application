package encryptionapp.services.encrypt;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

public interface IEncryptionService {

     String encrypt(Resource resource);
}
