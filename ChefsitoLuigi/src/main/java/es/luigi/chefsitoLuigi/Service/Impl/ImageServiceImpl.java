package es.luigi.chefsitoLuigi.Service.Impl;

import es.luigi.chefsitoLuigi.Service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final Path root = Paths.get("uploads");

    public ImageServiceImpl() throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
    }

    @Override
    public String store(MultipartFile file) throws IOException {
        String ext = "";
        String original = file.getOriginalFilename();
        if (original != null && original.contains(".")) ext = original.substring(original.lastIndexOf('.'));
        String filename = UUID.randomUUID().toString() + ext;
        Path dest = root.resolve(filename);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
        }
        // public path served by WebConfig: /uploads/{filename}
        return "/uploads/" + filename;
    }
}
