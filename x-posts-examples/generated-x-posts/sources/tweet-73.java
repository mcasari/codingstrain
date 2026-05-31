// ❌ Nested null checks — verbose, easy to forget a level
User user = userRepository.findByEmail(email);
if (user != null) {
    Address address = user.getAddress();
    if (address != null) {
        return address.getCity();
    }
}
return "unknown";

// ✅ Optional — chain safely when each step may be absent
return userRepository.findByEmail(email)
    .map(User::getAddress)
    .map(Address::getCity)
    .orElse("unknown");

// ✅ Or one explicit guard when Optional is not available
User user = userRepository.findByEmail(email);
if (user == null || user.getAddress() == null) {
    return "unknown";
}
return user.getAddress().getCity();
