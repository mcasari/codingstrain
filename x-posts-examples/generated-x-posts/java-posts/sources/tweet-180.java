import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

Path src = Path.of("data.csv");
Path backup = Path.of("data.csv.bak");

try {
    Files.copy(src, backup, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("Copied to " + backup);
} catch (IOException e) {
    throw new RuntimeException(e);
}
