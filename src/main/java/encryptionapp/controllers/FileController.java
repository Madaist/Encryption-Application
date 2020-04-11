package encryptionapp.controllers;

import encryptionapp.services.IEncryptionService;
import encryptionapp.services.IStorageService;
import encryptionapp.exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;

@Controller
public class FileController {

    private final IStorageService storageService;
    private final IEncryptionService encryptionService;
    //private static final Logger logger = (Logger) LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileController(IStorageService storageService, IEncryptionService encryptionService) {
        this.storageService = storageService;
        this.encryptionService = encryptionService;
    }
/*
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = storageService.loadAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type");
            //logger.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
*/
    @PostMapping("/")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                     RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + ". We're now encrypting.");
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