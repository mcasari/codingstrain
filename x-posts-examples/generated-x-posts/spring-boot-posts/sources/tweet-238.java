// ❌ Polling — keep asking “any news?” on a timer
@Scheduled(fixedDelay = 60_000)
void poll() {
    var events = github.fetchNewEvents(); // wasteful
    events.forEach(this::handle);
}

// ✅ Webhook — they POST when something happens
@RestController
class GitHubWebhook {
    @PostMapping("/webhooks/github")
    ResponseEntity<Void> onEvent(
            @RequestHeader("X-Hub-Signature-256") String sig,
            @RequestBody String body) {
        verify(sig, body); // reject fakes
        handle(body);
        return ResponseEntity.ok().build();
    }
}
