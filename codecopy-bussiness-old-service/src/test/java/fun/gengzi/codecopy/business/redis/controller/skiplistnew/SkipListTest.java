package fun.gengzi.codecopy.business.redis.controller.skiplistnew;

import org.junit.Test;

public class SkipListTest {

    @Test
    public void fun01(){

        SkipList<Integer> integerSkipList = new SkipList<>();
        // head <-------> tail
        integerSkipList.put(1,1);



        integerSkipList.put(2,2);
        integerSkipList.put(6,6);
        integerSkipList.put(3,3);
        integerSkipList.put(4,4);
        integerSkipList.put(5,5);
        String string = integerSkipList.toString();
        System.out.println(string);

        SkipListNode<Integer> search = integerSkipList.search(4);
        System.out.println(search.getValue());


    }


}
