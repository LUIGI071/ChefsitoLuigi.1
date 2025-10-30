package es.luigi.chefsitoLuigi.Service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImageService {
    /**
     * Guarda un archivo y devuelve la URL pública (p. ej. /uploads/filename.jpg)
     */
    String store(MultipartFile file) throws IOException;
}
