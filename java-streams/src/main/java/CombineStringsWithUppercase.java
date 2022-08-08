import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CombineStringsWithUppercase {
    /**
     * Convert String to uppercase and Join them with coma
     * @param args
     */
    public static void main(String[] args) {
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String combined = G7.stream().map(String::toUpperCase).collect(Collectors.joining());
        System.out.println(combined);
    }
}
