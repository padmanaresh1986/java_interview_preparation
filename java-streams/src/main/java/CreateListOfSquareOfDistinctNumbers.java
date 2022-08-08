import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateListOfSquareOfDistinctNumbers {

    /**
     * Create a List of the square of all distinct numbers and sort them using ascending order
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> resut = numbers.stream()
                .distinct()
                .map(num -> num * num)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(resut);
    }
}
