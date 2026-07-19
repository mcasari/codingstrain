@Autowired
CloudPlatform platform;

@GetMapping("/runtime")
Map<String, Object> runtime() {
    return Map.of(
        "platform", platform,
        "isEcs", platform == CloudPlatform.ECS,
        "isK8s", platform == CloudPlatform.KUBERNETES
    );
}

// Useful for conditional beans:
@ConditionalOnCloudPlatform(CloudPlatform.ECS)
@Configuration
class EcsTuningConfig { }
