// Constructor injection beats field injection:
//
// public UserController(UserRepository repository) {
//   this.repository = repository;
// }
//
// Spring recommends it. Your tests will thank you.
