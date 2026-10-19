// ❌ Legacy File I/O — verbose, easy to leak readers
File file = new File("data.txt");
BufferedReader reader = new BufferedReader(new FileReader(file));
String line;
while ((line = reader.readLine()) != null) {
    System.out.println(line);
}
reader.close();

// ✅ NIO.2 Path + Files — short and portable
Path path = Path.of("data.txt");
String text = Files.readString(path);
Files.writeString(Path.of("out.txt"), text);
List<String> lines = Files.readAllLines(path);
