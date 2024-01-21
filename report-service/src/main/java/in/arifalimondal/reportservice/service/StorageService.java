package in.arifalimondal.reportservice.service;

import in.arifalimondal.reportservice.entity.FileData;
import in.arifalimondal.reportservice.entity.ImageData;
import in.arifalimondal.reportservice.repository.FileDataRepository;
import in.arifalimondal.reportservice.repository.StorageRepository;
import in.arifalimondal.reportservice.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Value("${file.path}")
    private String FOLDER_PATH;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = storageRepository.save(ImageData.builder()
                                                .name(file.getOriginalFilename())
                                                .type(file.getContentType())
                                                .imageData(ImageUtils.compressImage(file.getBytes()))
                                                .build());

        if (imageData != null){
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) {
        return null;
    }

    public String uploadImageToFileSystem(MultipartFile file)  throws IOException {

        String filePath = FOLDER_PATH + "//" + file.getOriginalFilename();

        //final Path filePath2 = Path.of(FOLDER_PATH, file.getOriginalFilename());

        file.transferTo(new File(filePath));

        FileData fileData = fileDataRepository.save(FileData.builder()
                                            .name(file.getOriginalFilename())
                                            .type(file.getContentType())
                                            .filePath(filePath)
                                            .build());

        if (fileData != null) {
            return "File uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        if (fileData.isPresent()) {
            String filePath = fileData.get().getFilePath();
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        }

        return null;
    }
}
