// ❌ A NullPointerException waiting to happen
User u = repo.find(id);
return u.getEmail();

// ✅ Make "maybe absent" explicit
return repo.findById(id)
    .map(User::getEmail)
    .orElse("no-email");
