import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Arrays.stream(numbers).filter(n -> n > 4).forEach(System.out::println);

        List<Double> numList = Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5);
        System.out.println(closestNumber(4.3, numList));

        // 1st argument, init value = 0
        int sum = Arrays.stream(numbers).reduce(0, (a,b) -> a + b);
        int sum1 = Arrays.stream(numbers).reduce(0, (a, b) -> a + b);
        Arrays.stream(numbers).reduce(Integer::sum).ifPresent(System.out::println);
        int max = Stream.of(1,3,4,5,0,9).reduce(0, (a,b) -> a > b ? a : b);
        int max1 = Arrays.stream(numbers).reduce(0, Integer::max);
        Stream.of(4,1,5,0,9).reduce(Math::max).ifPresent(System.out::println);

        int min = Arrays.stream(numbers).reduce(0, (a,b) -> a <b ? a : b);
        int min1 = Arrays.stream(numbers).reduce(0, Integer::min);

        // Joining string
        String[] strings = {"a", "b", "c", "d", "e"};

        // |a|b|c|d|e, the intial | join is not what we want
        String reduce = Arrays.stream(strings).reduce("", (a,b) -> a + "|" + b);
        System.out.println(reduce);

        // a|b|c|d|e, filter the intial "" empty string
        String reduce2 = Arrays.stream(strings).reduce("", (a,b) -> {
            if (!"".equals(a)) {
                return a + "|" + b;
            } else {
                return b;
            }
        });

        System.out.println(reduce2);

        // a|b|c|d|e, better uses Java 8 String.join
        String join = String.join("|", strings);
        System.out.println(join);

        invoiceSum();

        System.out.println("https://mkyong.com/java8/java-8-stream-reduce-examples/");

        // stream.min, max
        List<Integer> list = Arrays.asList(-9, -18, 0, 25, 4);
        Optional<Integer> var1 = list.stream().max(Integer::compare);
        if (var1.isPresent()) {
            System.out.println(var1);
        }

        Integer var2 = list.stream().max(Comparator.reverseOrder()).get();
        System.out.println(var2);

        List<String> list1 = Arrays.asList("G", "E", "E", "K", "g", "e", "e", "k");
        list1.stream().max(Comparator.comparing(String::valueOf)).ifPresent(System.out::println);

        String[] array = {"Geeks", "for", "GeeksforGeeks", "GeeksQuiz"};

        // filer based on their last characters and returns the maximum value accrodingly
        Optional<String> Max2 = Arrays.stream(array).max((str1, str2) -> Character.compare(str1.charAt(str1.length() - 1),
                str2.charAt(str2.length() -1)));

        if (Max2.isPresent()) {
            System.out.println(Max2.get());
        }

        //    //first way to convert one type to another
//    List<Employee> getEmployee(List<Student> students) {
//        return students.stream().map(this::getEmployeeInfo).collect(toList());
//    }
//
//    //second way
//    List<Employee> getEmployee1(List<Student> students) {
//        return students.stream().map(student -> {
//            Employee employee = new Employee();
//            employee.setName(student.getName());
//            return employee;
//        }).collect(Collectors.toList());
//    }
//
//    List<Employee> getEmployee2(List<Student> students) {
//        return students.stream().map(student -> {
//            String name = student.getName() != null ? student.getName() : null;
//            return new Employee(name, 30, 2000.00);
//        }).collect(Collectors.toList())
//    }
    }

    public static void invoiceSum() {
        List<Invoice> invoices = Arrays.asList(
                new Invoice("A01", BigDecimal.valueOf(9.99), BigDecimal.valueOf(1)),
                new Invoice("A02", BigDecimal.valueOf(19.99), BigDecimal.valueOf(1.5)),
                new Invoice("A03", BigDecimal.valueOf(4.99), BigDecimal.valueOf(2)));

        BigDecimal sumInvoice = invoices.stream().map(x -> x.getQty().multiply(x.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(sumInvoice);
        System.out.println(sumInvoice.setScale(2, RoundingMode.HALF_UP));

        Optional<Invoice> invoice = invoices.stream().reduce((a,b) -> a.getPrice().equals(b.getPrice()) ? a : b);
        System.out.println(invoice);
        Optional<Invoice> invoice1 = invoices.stream().reduce((a,b) -> !a.getPrice().equals(b.getPrice()) ? a : b);
        System.out.println(invoice1);
        invoices.stream().max(Comparator.comparing(Invoice::getPrice)).ifPresent(System.out::println);
        Optional<Invoice> min = invoices.stream().min(Comparator.comparing(Invoice::getPrice));
        System.out.println(min);
    }

    static double closestNumber(double length, List<Double> lengthList) {
        double ans = 0;
        double min = Double.MAX_VALUE;

        for (Double d : lengthList) {
            double diff = Math.abs(length - d);
            if (diff < min) {
                min = diff;
                ans = d;
            }
        }
        return ans;
    }

    static List<Double> complexMap(double length, List<Double> lengthList) {

        return lengthList.stream().map(d -> {
            if (d > length) {
                return d;
            }
            return d;
        }).collect(Collectors.toList());
    }


    private static class Invoice {

        String invoiceNo;
        BigDecimal price;
        BigDecimal qty;

        public Invoice(String invoiceNo, BigDecimal price, BigDecimal qty) {
            this.invoiceNo = invoiceNo;
            this.price = price;
            this.qty = qty;
        }

        public String getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getQty() {
            return qty;
        }

        public void setQty(BigDecimal qty) {
            this.qty = qty;
        }

        @Override
        public String toString() {
            return "Invoice{" +
                    "invoiceNo='" + invoiceNo + '\'' +
                    ", price=" + price +
                    ", qty=" + qty +
                    '}';
        }
    }
}
