package com.codingstrain.filescopy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class FilesCopyDemo {

    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("files-copy-demo-");
        Path src = dir.resolve("data.csv");
        Path backup = dir.resolve("data.csv.bak");

        Files.writeString(src, "id,name\n1,Alice\n2,Bob\n", StandardCharsets.UTF_8);
        Files.copy(src, backup, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Source : " + src);
        System.out.println("Backup : " + backup);
        System.out.println();
        System.out.println("Backup contents:");
        System.out.println(Files.readString(backup, StandardCharsets.UTF_8));
    }
}
