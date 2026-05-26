@Query("SELECT u FROM User u")
Stream<User> streamAllUsers();
