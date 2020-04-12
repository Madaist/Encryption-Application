package encryptionapp.controllers;

import encryptionapp.services.encrypt.IEncryptionService;
import encryptionapp.services.storage.IStorageService;
import encryptionapp.exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

@Controller
public class FileController {

    private final IStorageService storageService;
    private final IEncryptionService encryptionService;

    @Autowired
    public FileController(IStorageService storageService, IEncryptionService encryptionService) {
        this.storageService = storageService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> handleEncryptFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);
        Resource resource = storageService.loadAsResource(file.getOriginalFilename());
        String encryptedMessage = encryptionService.encrypt(resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/plain"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=encrypted.txt")
                .body(encryptedMessage);
    }


    @PostMapping("/decrypt")
    public ResponseEntity<String> handleDecryptFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);
        Resource resource = storageService.loadAsResource(file.getOriginalFilename());
        String decryptedMessage = encryptionService.decrypt(resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/plain"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=decrypted.txt")
                .body(decryptedMessage);
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}