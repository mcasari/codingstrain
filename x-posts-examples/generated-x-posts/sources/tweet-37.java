CSVParser csvParser = new CSVParser(reader,
    CSVFormat.DEFAULT.withHeader());
String name = record.get("name");
