@Bean
@Profile("dev")
DataSource devDataSource() { return new H2DataSource(); }

@Bean
@Profile("prod")
DataSource prodDataSource() { return new PgDataSource(); }
