import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

public class FindSummaryStatisticsOfNumbers {

    /**
     * WAP to Get count, min, max, sum, and the average for numbers
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics intSummaryStatistics = primes.stream().mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("Sum : "+intSummaryStatistics.getSum());
        System.out.println("Avg : "+intSummaryStatistics.getAverage());
        System.out.println("Max : "+intSummaryStatistics.getMax());
        System.out.println("Min : "+intSummaryStatistics.getMin());
        System.out.println("Count : "+intSummaryStatistics.getCount());

    }
}
