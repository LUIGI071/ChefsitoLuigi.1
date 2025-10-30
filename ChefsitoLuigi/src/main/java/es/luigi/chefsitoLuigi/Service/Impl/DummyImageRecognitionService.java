package es.luigi.chefsitoLuigi.Service.Impl;

import es.luigi.chefsitoLuigi.Service.ImageRecognitionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class DummyImageRecognitionService implements ImageRecognitionService {
    @Override
    public List<String> recognizeIngredients(MultipartFile file) {
        // TODO: integrar Google Vision/API real.
        // Por ahora devolvemos lista vacía o placeholder.
        return List.of(); // o List.of("tomate","cebolla") para pruebas
    }
}
