// Java Tip 💡: Try to avoid static variables unless they store truly global values.
//
// • They hinder testing by making it harder to isolate classes due to shared state.
// • They reduce code reusability. For example:
//
// public class Config {
//     ...
//     public static String getConfigPath() {
//         return configPath;
//     }
// }
//
// The configuration path is shared globally, and changing it in one place could affect others.
