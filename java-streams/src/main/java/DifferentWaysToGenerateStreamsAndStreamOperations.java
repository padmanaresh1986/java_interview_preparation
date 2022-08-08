import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

public class DifferentWaysToGenerateStreamsAndStreamOperations {
    /**
     * What are the different ways to generate streams in java
     * @param args
     */
    public static void main(String[] args) throws Exception{

        // empty stream, mostly used to avoid null pointer exceptions
        Stream<String> streamEmpty = Stream.empty();
        /*
        public Stream<String> streamOf(List<String> list) {
            return list == null || list.isEmpty() ? Stream.empty() : list.stream();
        }
        */

        // Stream of Collection
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();

        //Stream of Array
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);
        Stream<String> streamOfArray = Stream.of("a", "b", "c");

        //Stream builder
        Stream<String> streamBuilder =  Stream.<String>builder().add("a").add("b").add("c").build();

        //Stream Generate
        Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);
        //The code above creates a sequence of ten strings with the value “element.”

        //Stream iterate
        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        //The first element of the resulting stream is the first parameter of the iterate() method. When creating every following element, the specified function is applied to the previous element. In the example above the second element will be 42.

        //Stream of Primitives
        IntStream intStream = IntStream.range(1, 3);
        LongStream longStream = LongStream.rangeClosed(1, 3);

        //Random Stream
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
        //Since Java 8, the Random class provides a wide range of methods for generating streams of primitives. For example, the following code creates a DoubleStream, which has three elements:

        //Stream of String
        IntStream streamOfChars = "abc".chars();
        Stream<String> streamOfString =  Pattern.compile(", ").splitAsStream("a, b, c");

        //Stream of File
        Path path = Paths.get("C:\\file.txt");
        Stream<String> streamOfStrings = Files.lines(path);
        Stream<String> streamWithCharset = Files.lines(path, Charset.forName("UTF-8"));

        //Referencing a Stream

        Stream<String> stream =  Stream.of("a", "b", "c").filter(element -> element.contains("b"));
        Optional<String> anyElement = stream.findAny();
        Optional<String> firstElement = stream.findFirst();

        //Stream Pipeline
        Stream<String> onceModifiedStream = Stream.of("abcd", "bbcd", "cbcd").skip(1);
        Stream<String> twiceModifiedStream =  stream.skip(1).map(element -> element.substring(0, 3));
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        long size = list.stream().skip(1).map(element -> element.substring(0, 3)).sorted().count();

        // Stream Reduction

        //The reduce() Method
        OptionalInt reduced = IntStream.range(1, 4).reduce((a, b) -> a + b); // 6
        int reducedTwoParams = IntStream.range(1, 4).reduce(10, (a, b) -> a + b); // 16
        //combiner gets called only when parallel stream happens
        int reducedParallel = Arrays.asList(1, 2, 3).parallelStream().reduce(10, (a, b) -> a + b, (a, b) -> a + b);

        //The collect() Method
        List<Product> productList = Arrays.asList(new Product(23, "potatoes"),
                new Product(14, "orange"), new Product(13, "lemon"),
                new Product(23, "bread"), new Product(13, "sugar"));

        //Converting a stream to the Collection (Collection, List or Set):
        List<String> collectorCollection =  productList.stream().map(Product::getName).collect(Collectors.toList());

        //Reducing to String:
        String listToString = productList.stream().map(Product::getName).collect(Collectors.joining(", ", "[", "]"));

       // Processing the average value of all numeric elements of the stream:
        double averagePrice = productList.stream().collect(Collectors.averagingInt(Product::getPrice));

        //Processing the sum of all numeric elements of the stream:
        int summingPrice = productList.stream().collect(Collectors.summingInt(Product::getPrice));

        //Collecting statistical information about stream’s elements:
        IntSummaryStatistics statistics = productList.stream().collect(Collectors.summarizingInt(Product::getPrice));

        //Grouping of stream’s elements according to the specified function:
        Map<Integer, List<Product>> collectorMapOfLists = productList.stream()
                .collect(Collectors.groupingBy(Product::getPrice));

        //Dividing stream’s elements into groups according to some predicate:
        Map<Boolean, List<Product>> mapPartioned = productList.stream()
                .collect(Collectors.partitioningBy(element -> element.getPrice() > 15));


        //Pushing the collector to perform additional transformation:
        Set<Product> unmodifiableSet = productList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toSet(),
                        Collections::unmodifiableSet));

        //Custom collector:
        Collector<Product, ?, LinkedList<Product>> toLinkedList =
                Collector.of(LinkedList::new, LinkedList::add,
                        (first, second) -> {
                            first.addAll(second);
                            return first;
                        });

        LinkedList<Product> linkedListOfPersons =
                productList.stream().collect(toLinkedList);



    }
}

class Product {
    int price;
    String name;

    public Product(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
