import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public interface UserRepository {

    @NonNull
    Optional<User> findById(@NonNull Long id);

    // Explicit: may return null if cache miss + DB down
    @Nullable
    User findCached(@NonNull Long id);
}

// Call sites: IDE / NullAway can flag unsafe unwraps
User user = repo.findById(id).orElseThrow();
String email = Objects.requireNonNull(user.email());
