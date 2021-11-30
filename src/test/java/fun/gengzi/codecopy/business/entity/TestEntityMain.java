package fun.gengzi.codecopy.business.entity;

import com.alibaba.fastjson.JSON;

public class TestEntityMain {

    public static void main(String[] args) {

//        TestEntity testEntity = new TestEntity();
////        testEntity.setEntity(new TestEntity.TestNeerEntity());
//
//        TestEntity.TestNeerEntity testNeerEntity = testEntity.new TestNeerEntity();
//        testNeerEntity.setParm1("牛");
//        ArrayList<TestEntity.TestNeerEntity> objects = new ArrayList<>();
//        boolean add = objects.add(testNeerEntity);
//        testEntity.setEntity(objects);
//
//        String s = JSON.toJSONString(testEntity);
//        System.out.println(s);

        TestEntity testEntity1 = JSON.parseObject("{\"entity\":[{\"parm1\":\"牛\"}]}", TestEntity.class);

        System.out.println(testEntity1.getEntity());
    }
}
