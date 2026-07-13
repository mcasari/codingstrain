return new StepBuilder("step", jobRepository)
    .<Person, Person>chunk(5, transactionManager)
    .reader(reader())
    .processor(processor())
    .writer(writer())
    .build();
