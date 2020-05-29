package fun.gengzi.codecopy.java8.lamdba.utils;

public class Person{
    Integer id;
    String name;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Person() {
        this.name="mike";
                    System.out.println("创建了一个对象");
    }
    public Person(Integer x,String y) {
        // TODO Auto-generated constructor stub
        this.id = x;
        this.name=y;
    }
//        public String toString() {
//            return this.name;
//        }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}