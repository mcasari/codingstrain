package examples;
public class BuilderDemo {

    public static void main(String[] args) {
        // Create a User using the builder
        User user1 = new User.Builder("Mary", 30)
                .email("mary@example.com")
                .address("Italy")
                .build();

        User user2 = new User.Builder("Nick", 25)
                .phone("+39 144 456 555")
                .build();

        System.out.println(user1);
        System.out.println(user2);
    }
}

// The class we want to build
class User {
    private final String name;
    private final int age;
    private final String email;
    private final String phone;
    private final String address;
    private final String address2; 
    private final String height; 
    private final String weight;

    // Private constructor: only Builder can create instances
    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.address2 = builder.address2;
        this.height = builder.height;
        this.weight = builder.weight;
    }

    // Static nested Builder class
    public static class Builder {
        private final String name; // required
        private final int age;     // required
        private String email;      // optional
        private String phone;      // optional
        private String address;    // optional
        private String address2;    // optional
        private String height;    // optional
        private String weight;    // optional

        public Builder(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder address2(String address2) {
            this.address2 = address2;
            return this;
        }       
        
        public Builder height(String height) {
            this.height = height;
            return this;
        }  
        
        public Builder weight(String weight) {
            this.weight = weight;
            return this;
        }  

		public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}