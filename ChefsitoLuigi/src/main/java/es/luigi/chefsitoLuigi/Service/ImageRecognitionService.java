package es.luigi.chefsitoLuigi.Service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ImageRecognitionService {
    /**
     * Reconoce ingredientes a partir de la imagen.
     * Implementación dummy devuelve lista vacía.
     * Integrar Google Vision/Cloud ML en implementación real.
     */
    List<String> recognizeIngredients(MultipartFile file);
}
