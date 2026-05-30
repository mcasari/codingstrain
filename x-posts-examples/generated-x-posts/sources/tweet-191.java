@Scheduled(fixedRate = 5000)
public void poll() {
    log.info("tick");
}
// Enable with @EnableScheduling on a @Configuration class
