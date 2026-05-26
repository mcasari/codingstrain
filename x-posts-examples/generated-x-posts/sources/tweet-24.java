@EnableAsync
@SpringBootApplication
public class DemoApplication { }

@Async("taskExecutor")
public void runTask() { }
