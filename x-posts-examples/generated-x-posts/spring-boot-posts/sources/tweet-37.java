try (Reader reader = Files.newBufferedReader(path);
     CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
    for (CSVRecord record : parser) {
        String name = record.get("name");
    }
}
