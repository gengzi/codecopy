package fun.gengzi.codecopy.java8.lamdba.utils;

import java.util.List;
import java.util.Optional;

public class Person{
    Integer id;
    String name;
    Optional<List<Role>> roles;


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


    public Optional<List<Role>> getRoles() {
        return roles;
    }

    public void setRoles(Optional<List<Role>> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}