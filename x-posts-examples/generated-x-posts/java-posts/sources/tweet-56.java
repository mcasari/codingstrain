import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

class SharedState {
    boolean ready;   // plain field — no volatile needed
    int value;
}

static final VarHandle READY;
static {
    try {
        READY = MethodHandles.lookup()
            .findVarHandle(SharedState.class, "ready", boolean.class);
    } catch (ReflectiveOperationException e) {
        throw new ExceptionInInitializerError(e);
    }
}

SharedState state = new SharedState();

// Writer thread
state.value = 42;
READY.setRelease(state, true);       // publish: "I'm done writing"

// Reader thread
while (!(boolean) READY.getAcquire(state)) {
    Thread.onSpinWait();             // subscribe: wait for publish
}
System.out.println(state.value);   // guaranteed 42 — no stale read
