package com.codingstrain.filescopy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class FilesCopyDemo {

    public static void main(String[] args) throws IOException {
        Path sampleCsv = resolveSampleCsv();
        Path dir = Files.createTempDirectory("files-copy-demo-");
        Path src = dir.resolve("data.csv");
        Path backup = dir.resolve("data.csv.bak");

        Files.copy(sampleCsv, src, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(src, backup, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Sample : " + sampleCsv.toAbsolutePath());
        System.out.println("Source : " + src);
        System.out.println("Backup : " + backup);
        System.out.println();
        System.out.println("Backup contents:");
        System.out.println(Files.readString(backup, StandardCharsets.UTF_8));
    }

    /**
     * Finds {@code data.csv} on the classpath (after {@code mvn compile}) or on disk
     * at {@code src/main/resources/data.csv} when running from the IDE.
     */
    static Path resolveSampleCsv() throws IOException {
        URL resource = FilesCopyDemo.class.getClassLoader().getResource("data.csv");
        if (resource != null && "file".equals(resource.getProtocol())) {
            try {
                return Path.of(resource.toURI());
            } catch (URISyntaxException e) {
                throw new IOException("Bad resource URI: " + resource, e);
            }
        }
        if (resource != null) {
            Path extracted = Files.createTempFile("files-copy-demo-", ".csv");
            try (InputStream in = resource.openStream()) {
                Files.copy(in, extracted, StandardCopyOption.REPLACE_EXISTING);
            }
            return extracted;
        }

        Path fromDisk = findOnDisk(Path.of(System.getProperty("user.dir")));
        if (fromDisk != null) {
            return fromDisk;
        }

        throw new IllegalStateException(
                "Could not find data.csv. Run from the module folder after placing the file at "
                        + "src/main/resources/data.csv, or run: mvn -q compile exec:java");
    }

    private static Path findOnDisk(Path start) {
        for (Path base = start; base != null; base = base.getParent()) {
            for (String relative : new String[] {
                    "src/main/resources/data.csv",
                    "x-posts-examples/java-files-copy-demo/src/main/resources/data.csv"
            }) {
                Path candidate = base.resolve(relative);
                if (Files.isRegularFile(candidate)) {
                    return candidate;
                }
            }
        }
        return null;
    }
}
