users.sort(
    Comparator.comparing(User::getLastName)
              .thenComparing(User::getFirstName));
