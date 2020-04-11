package encryptionapp.services;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

public interface IEncryptionService {

     File encrypt(Resource resource);
}
