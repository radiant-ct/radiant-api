package dev.pepecoral.radiant.modules.common.builders;

import java.util.Random;

/**
 * Utility class for generating random names and text.
 * <p>
 * The random name format is:
 * {@code [prefix-]adjective1-adjective2-animal-randomNumber}.
 * You can optionally provide a prefix, or leave it null to omit.
 * <p>
 * Example usage:
 * 
 * <pre>
 * String name1 = SampleDataGenerator.generateName();
 * String name2 = SampleDataGenerator.generateName("Epic");
 * String randomText = SampleDataGenerator.generateText(20);
 * </pre>
 * 
 * This class also allows generating arbitrary random text sequences using a
 * predefined word list.
 */
public class SampleDataGenerator {

    private static final String[] ADJECTIVES1 = {
            "brave", "mysterious", "ancient", "swift", "fierce", "golden", "silent", "shadowy",
            "radiant", "icy", "bold", "noble", "sly", "vivid", "wild", "grim", "quiet", "stormy",
            "gleaming", "celestial", "fiery", "frozen", "vigilant", "serene", "majestic", "shimmering",
            "luminous", "ominous", "gentle", "sturdy", "daring", "proud", "trembling", "arcane", "roaring",
            "glorious", "elegant", "veiled", "secretive", "enchanted", "mystic", "sparkling", "tenacious",
            "wicked", "tender", "mighty", "ethereal", "haunting", "resolute", "blazing", "radiating"
    };

    private static final String[] ADJECTIVES2 = {
            "wily", "graceful", "luminous", "thundering", "cunning", "vigilant", "playful", "majestic",
            "stoic", "gleaming", "quiet", "feral", "shy", "swift", "tremendous", "hidden", "fabled", "prismatic",
            "bold", "charming", "fiery", "ethereal", "shadowed", "crimson", "brilliant", "silent", "ancient",
            "dazzling", "murky", "valiant", "elegant", "grim", "gleaming", "veiled", "celestial", "playful",
            "stormy", "arcane", "mystic", "radiant", "ominous", "gentle", "sturdy", "daring", "proud",
            "trembling", "enchanted", "blazing", "resolute", "glimmering", "phantom", "vivid", "hallowed"
    };

    private static final String[] ANIMALS = {
            "fox", "owl", "wolf", "tiger", "bear", "eagle", "panther", "lion", "falcon", "dragon",
            "cobra", "serpent", "lynx", "stag", "hawk", "raven", "leopard", "jaguar", "phoenix", "griffin",
            "hydra", "boar", "coyote", "cougar", "shark", "kraken", "dragonfly", "mantis", "owlbear", "direwolf",
            "viper", "ram", "bison", "jackal", "scorpion", "falconet", "gecko", "wolfhound", "lynxcat", "basilisk",
            "otter", "beetle", "stagbeetle", "python", "sparrow", "condor", "mongoose", "badger", "ermine", "catfish"
    };

    private static final String[] WORDS = {
            "whisper", "storm", "echo", "flame", "glimmer", "void", "twilight", "ember", "frost", "horizon",
            "pulse", "rift", "veil", "drift", "shard", "cascade", "ember", "shadow", "spark", "glow", "dawn",
            "gale", "crystal", "shiver", "thorn", "sparkle", "mirror", "veil", "mist", "gleam", "torrent", "shade",
            "emberlight", "rift", "spire", "echo", "quiver", "lumen", "frostbite", "radiance", "echoing", "voided",
            "flurry", "phantom", "twist", "arc", "crevice", "shimmer", "blaze", "driftwood", "vortex", "moonlight"
    };

    private static final Random RANDOM = new Random();

    /**
     * Generates a random name without a prefix.
     *
     * @return a random name in the format adjective1-adjective2-animal-randomNumber
     */
    public static String generateName() {
        return generateName(null);
    }

    /**
     * Generates a random name with an optional prefix.
     *
     * @param prefix the prefix to prepend to the name, or {@code null} to omit
     * @return a random name in the format
     *         [prefix-]adjective1-adjective2-animal-randomNumber
     */
    public static String generateName(String prefix) {
        String adj1 = ADJECTIVES1[RANDOM.nextInt(ADJECTIVES1.length)];
        String adj2 = ADJECTIVES2[RANDOM.nextInt(ADJECTIVES2.length)];
        String animal = ANIMALS[RANDOM.nextInt(ANIMALS.length)];
        int number = RANDOM.nextInt(1000); // 0-999

        String name = adj1 + "-" + adj2 + "-" + animal + "-" + number;
        if (prefix != null && !prefix.isEmpty()) {
            name = prefix + "-" + name;
        }
        return name;
    }

    /**
     * Generates a random text string by combining random words from a predefined
     * list.
     *
     * @param length the number of words to generate
     * @return a space-separated string of random words
     */
    public static String generateText(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0)
                sb.append(" ");
            sb.append(WORDS[RANDOM.nextInt(WORDS.length)]);
        }
        return sb.toString();
    }

    // Example usage
    public static void main(String[] args) {
        System.out.println("Random Name: " + generateName());
        System.out.println("Random Name with Prefix: " + generateName("Epic"));
        System.out.println("Random Text: " + generateText(30));
    }
}