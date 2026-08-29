import java.nio.file.Files;
import java.nio.file.Path;

Path a = Path.of("data.txt");
Path b = Path.of("./data.txt");
Path link = Path.of("data-link.txt");  // symlink to data.txt

// ❌ Path.equals — string/name identity, not the file on disk
a.equals(b);       // often false
a.equals(link);    // false (different path text)

// ✅ Same actual file? Resolves relative paths + symlinks
Files.isSameFile(a, b);     // true (if both exist)
Files.isSameFile(a, link);  // true (link points at data.txt)

// Handy when cleaning duplicates or comparing user-supplied paths
if (Files.isSameFile(userPath, configPath)) {
    // treat as one file
}
