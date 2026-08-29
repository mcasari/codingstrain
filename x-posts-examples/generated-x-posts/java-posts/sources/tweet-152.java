Map<Dept, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDept));

long active = employees.stream()
    .filter(Employee::isActive)
    .count();
