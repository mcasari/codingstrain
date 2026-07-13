// ✅ Auto-closes the stream, even if an exception is thrown
try (BufferedReader reader =
         new BufferedReader(new FileReader("data.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
// No finally block, no leaked file handles
