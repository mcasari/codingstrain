@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
// Controllers stay clean — no try/catch everywhere
