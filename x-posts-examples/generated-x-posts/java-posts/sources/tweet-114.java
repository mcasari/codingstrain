// ❌ Manual recursion with File — verbose and easy to miss dirs
void findJava(File dir, List<File> out) {
    File[] kids = dir.listFiles();
    if (kids == null) return;
    for (File f : kids) {
        if (f.isDirectory()) findJava(f, out);
        else if (f.getName().endsWith(".java")) out.add(f);
    }
}

// ✅ Files.walk — stream the tree, filter, collect
try (Stream<Path> paths = Files.walk(Path.of("src"))) {
    List<Path> javaFiles = paths
        .filter(p -> p.toString().endsWith(".java"))
        .toList();
}
