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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @PostMapping("/")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);
        Resource resource = storageService.loadAsResource(file.getOriginalFilename());

        String encryptedMessage = encryptionService.encrypt(resource);
        String encryptedFileName = "encrypted.txt";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/plain"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encryptedFileName+ "\"")
                .body(encryptedMessage.toString());
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}