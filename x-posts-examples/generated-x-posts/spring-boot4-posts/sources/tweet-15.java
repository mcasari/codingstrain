@Component
@Order(1)
class MdcTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        return () -> {
            if (context != null) MDC.setContextMap(context);
            try { runnable.run(); }
            finally { MDC.clear(); }
        };
    }
}

@Component
@Order(2)
class SecurityTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        var auth = SecurityContextHolder.getContext();
        return () -> {
            SecurityContextHolder.setContext(auth);
            try { runnable.run(); }
            finally { SecurityContextHolder.clearContext(); }
        };
    }
}
