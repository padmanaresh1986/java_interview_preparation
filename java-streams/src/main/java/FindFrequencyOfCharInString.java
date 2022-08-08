import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindFrequencyOfCharInString {

    /**
     * Write a program to find frequency of a character in a string
     * @param args
     */
    public static void main(String[] args) {
        String s = "java is awesome";
        Map<String, Long> cahrCountMap = Stream.of(s.split(""))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(cahrCountMap);
    }
}
