package in.arifalimondal.reportservice.web;

import in.arifalimondal.reportservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {

        String uploadImage = storageService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.OK)
                                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageDate = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.valueOf("image/png"))
                            .body(imageDate);
    }

    @PostMapping("/fileSystem")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image")
                                                     MultipartFile file) throws IOException {
        String uploadImage = storageService.uploadImageToFileSystem(file);

        return ResponseEntity.status(HttpStatus.OK)
                            .body(uploadImage);
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageByte = storageService.downloadImageFromFileSystem(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.valueOf("image/png"))
                            .body(imageByte);
    }
}
