import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExploringCollectors {
    public static void main(String[] args) {
        List<Person> people = Sample.createPeople();

        //iterate all persons
        people.stream().forEach(System.out::println);
        System.out.println("##########################################");
        //filter people whose age grater than 32
        people.stream().filter(person -> person.getAge() > 32).forEach(System.out::println);
        System.out.println("##########################################");
        //print only names of the people whose age is grater than 32
        people.stream().filter(person -> person.getAge() > 32).map(Person::getName).forEach(System.out::println);
        System.out.println("##########################################");
        //calculate the sum all persons age
        Integer reduce = people.stream().map(Person::getAge).reduce(0, (total, age) -> total + age);
        System.out.println(reduce);
        Integer reduce1 = people.stream().map(Person::getAge).reduce(0, Integer::sum);
        System.out.println(reduce1);
        // create a list of names in upper case
        ArrayList<String> namesInUpperCase = people.stream().map(Person::getName).map(String::toUpperCase)
                .reduce(new ArrayList<String>(), // seed value
                        (names, name) -> { // tells how to handle each value
                            names.add(name);
                            return names;
                        },
                        (names1, names2) -> { // tells how to combine results in parallel
                            names1.addAll(names2);
                            return names1;
                        }
                );

        System.out.println(namesInUpperCase);
        System.out.println("##########################################");

        // using Collectors
        List<String> namesList = people.stream().map(Person::getName).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(namesList);

        List<String> namesListUnMod = people.stream().map(Person::getName).map(String::toUpperCase).collect(Collectors.toUnmodifiableList());
        System.out.println(namesListUnMod);

        Set<String> namesSet = people.stream().map(Person::getName).map(String::toUpperCase).collect(Collectors.toSet());
        System.out.println(namesSet);

        Set<String> namesSetUnMo = people.stream().map(Person::getName).map(String::toUpperCase).collect(Collectors.toUnmodifiableSet());
        System.out.println(namesSetUnMo);

        System.out.println("##########################################");
       // create map of name and age
        Map<String, Integer> nameAndAge = people.stream().distinct().collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println(nameAndAge);

        Map<String, Integer> nameAndAgeUnMo = people.stream().distinct().collect(Collectors.toUnmodifiableMap(Person::getName, Person::getAge));
        System.out.println(nameAndAgeUnMo);
        System.out.println("##########################################");
        // create a comma separate string of names
        String names = people.stream().map(Person::getName).map(String::toUpperCase).collect(Collectors.joining(","));
        System.out.println(names);

        String namesPrePost = people.stream().map(Person::getName).map(String::toUpperCase).collect(Collectors.joining(",","[","]"));
        System.out.println(namesPrePost);
        System.out.println("##########################################");
        //split the people into two groups with even and odd aged
        Map<Boolean, List<Person>> personsMap = people.stream().collect(Collectors.partitioningBy(person -> person.getAge() % 2 == 0));
        System.out.println(personsMap);
        System.out.println("##########################################");
        // group the people based on their name
        Map<String, List<Person>> personNameGroup = people.stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println(personNameGroup);
        System.out.println("##########################################");
        // group the people based on their name and have list of ages of person
        Map<String, List<Integer>> personNameAgeGroup = people.stream().collect(Collectors.groupingBy(Person::getName,Collectors.mapping(Person::getAge,Collectors.toList())));
        System.out.println(personNameAgeGroup);
        System.out.println("##########################################");
        //get the count of person having same name
        Map<String, Long> personNameAgeCount = people.stream().collect(Collectors.groupingBy(Person::getName,Collectors.counting()));
        System.out.println(personNameAgeCount);
        Map<String, Integer> personNameAgeCountAsInt = people.stream().collect(Collectors.groupingBy(Person::getName,
                Collectors.collectingAndThen(Collectors.counting(),Long::intValue)));
        System.out.println(personNameAgeCountAsInt);
        System.out.println("##########################################");
        // get the person with maximum and minimum age from the list
        Optional<Person> maxAgedPerson = people.stream().collect(Collectors.maxBy(Comparator.comparing(Person::getAge)));
        Optional<Person> minAgedPerson = people.stream().collect(Collectors.minBy(Comparator.comparing(Person::getAge)));
        // get the name of the person with max age , not the entire person object
        String personNameWithMaxAge1 = people.stream().collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Person::getAge)),
                        personOptional -> personOptional.map(Person::getName).orElse("")
                )
        );
        System.out.println(personNameWithMaxAge1);
        System.out.println("##########################################");
        // group the people based on their age and have list of names of person and filter out names of people whose name is more than 4 characters
        Map<Integer, List<String>> namesFiltering = people.stream().collect(Collectors.groupingBy(
                Person::getAge,
                Collectors.mapping(Person::getName,
                        Collectors.filtering(name -> name.length() > 4, Collectors.toList())
                )
        ));
        System.out.println(namesFiltering);
        System.out.println("##########################################");
        // flatmap example
        List<Integer> numbers = List.of(1,2,3,4,5,6,7);
        List<Integer> flatmapped = numbers.stream().flatMap(e -> List.of(e - 1, e + 1).stream()).collect(Collectors.toList());
        System.out.println(flatmapped);

        List<String> allCharactersOfAllNames = people.stream().map(Person::getName).flatMap(name -> Stream.of(name.split(""))).collect(Collectors.toList());
        System.out.println(allCharactersOfAllNames);
        //get all chars of persons
        var flatMappingNames = people.stream().collect(Collectors.groupingBy(Person::getAge,Collectors.flatMapping(p -> Arrays.stream(p.getName().split("")),Collectors.toList())));
        System.out.println(flatMappingNames);
        // get all chars of person in upper case
        System.out.println(
                people.stream().collect(Collectors.groupingBy(
                        Person::getAge,
                        Collectors.mapping(
                                P -> P.getName().toUpperCase(),
                                Collectors.flatMapping(p -> Arrays.stream(p.split("")), Collectors.toSet())
                        ))
                ));
    }
}

class Sample{
    public static List<Person> createPeople(){
        return List.of(
                new Person("Sara",20),
                new Person("Sara",22),
                new Person("Bob",20),
                new Person("Paula",32),
                new Person("Paul",32),
                new Person("Jack",3),
                new Person("Jack",72),
                new Person("Jill",11)
                );
    }
}

class Person{
    String name;
    Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
