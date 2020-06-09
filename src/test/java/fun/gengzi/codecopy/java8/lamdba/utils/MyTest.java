package fun.gengzi.codecopy.java8.lamdba.utils;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyTest {
    // 商人
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    // 交易
    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    /**
     * 找出2011年发生的所有交易，并按交易额排序（从低到高）。
     */
    @Test
    public void fun01() {

//        List<Transaction> collect = transactions.stream()
//                .filter(transaction -> transaction.getYear() == 2011)
//                .sorted((transaction1, transaction2) -> transaction1.getValue() - transaction2.getValue())
//                .collect(Collectors.toList());
        List<Transaction> collect = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList());

        collect.forEach(transaction -> System.out.println(transaction.toString()));

    }

    /**
     * (2) 交易员都在哪些不同的城市工作过？
     */
    @Test
    public void fun02() {
//
//        List<String> collect = transactions.stream()
//                .map(transaction -> transaction.getTrader())
//                .distinct()
//                .map(trader -> trader.getCity())
//                .distinct()
//                .collect(Collectors.toList());

        List<String> collect = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        collect.forEach(s -> System.out.println(s));


    }

    /**
     * 查找所有来自于剑桥的交易员，并按姓名排序。
     */
    @Test
    public void fun03() {

        List<Trader> cambridge = transactions.stream()
                .map(transaction -> transaction.getTrader())
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        cambridge.forEach(s -> System.out.println(s));


    }


    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序。
     */
    @Test
    public void fun04() {
        List<String> collect = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted(Comparator.comparingInt(s -> s.charAt(0)))
                .collect(Collectors.toList());


        collect.forEach(s -> System.out.println(s));


    }

    //有没有交易员是在米兰工作的？
    @Test
    public void fun05() {
        boolean collect = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(collect);
    }

    //打印生活在剑桥的交易员的所有交易额
    @Test
    public void fun06() {
        Integer cambridge = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getValue())
                .reduce(Integer::sum)
                .get();

        System.out.println(cambridge);
    }

    //所有交易中，最高的交易额是多少？
    @Test
    public void fun07() {
        Integer cambridge = transactions.stream()
                .map(transaction -> transaction.getValue())
                .reduce(Integer::max)
                .get();

        System.out.println(cambridge);
    }

    //找到交易额最小的交易。
    @Test
    public void fun08() {
        Transaction reduce = transactions.stream()
                .reduce(transactions.get(0),
                        (transaction1, transaction2) ->
                        {
                            if (transaction1.getValue() > transaction2.getValue()) {
                                return transaction2;
                            }
                            return transaction1;
                        }
                );

        // 简化模式
        Optional<Transaction> reduce1 = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() > t2.getValue() ? t2 : t1);

        System.out.println(reduce1.toString());

        IntStream range = IntStream.range(1, 4);
        range.forEach(index -> System.out.println(index));


        BigDecimal decimal = BigDecimal.ZERO;
        BigDecimal add = decimal.add(BigDecimal.valueOf(5));
        System.out.println(add.toString());
    }


    /**
     * 查找出分组： 各年的信息
     */
    @Test
    public void fun09(){

        // groupingBy 返回  Map<K, List<T>>, k 就是分组的条件，值是通过 tolist 转化的 list 对象
        Map<Integer, List<Transaction>> collect = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getYear()));


        collect.forEach((integer, transactions) -> {
            System.out.println(integer);
            transactions.forEach(s-> System.out.println(s.toString()));
        });
    }


    /**
     * flatMap 流的扁平化
         flatmap方法让你把一个流中的每个值都换成另一个流，然后把所有的流连接
         起来成为一个流
     *
     *
     */
    @Test
    public void fun10(){

        // groupingBy 返回  Map<K, List<T>>, k 就是分组的条件，值是通过 tolist 转化的 list 对象
        Map<Integer, List<Transaction>> collect = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getYear()));


        // 按照年份倒叙排序
//        TreeMap<Integer, List<Transaction>> integerObjectTreeMap = Maps.newTreeMap((o1, o2) -> -1);

        // 反向排序
        TreeMap<Transaction,Integer> integerObjectTreeMap = Maps.newTreeMap(Comparator.comparingInt(Transaction::getYear).reversed());

        HashMap<Transaction, Integer> transactionStringHashMap = new HashMap<>();

        AtomicInteger i = new AtomicInteger();

        transactions.forEach(transaction -> {
            i.addAndGet(1);
            int i1 = i.get();
            System.out.println(i1);
            transactionStringHashMap.put(transaction, transaction.getYear());
        });



//        TreeMap<Integer, Object> integerObjectTreeMap1 = Maps.newTreeMap(Comparator.comparingInt(Integer::intValue));



//        integerObjectTreeMap.putAll(collect);
//
//
//        integerObjectTreeMap.forEach((i,tr)->{
//            System.out.println(i);
//           tr.forEach(transaction -> System.out.println(transaction.toString()));
//        });


//
//        Stream<List<Transaction>> stream = collect.values().stream();
//
//        Stream<Stream<Transaction>> streamStream = stream.map(transactions1 -> transactions1.stream());
//
//        Stream<Transaction> transactionStream = stream.flatMap(transactions1 -> transactions1.stream());
//        List<Transaction> collect1 = transactionStream.collect(Collectors.toList());



    }


}
