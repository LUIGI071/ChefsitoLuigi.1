package es.luigi.chefsitoLuigi.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(resource + " not found with " + field + " = " + value);
    }
}
