package com.note.week3;

public class Entity implements ParentInterface{

    private Long id;
    private String name;

    public Entity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print(){
        System.out.println("Entity print 출력 : id는 " + this.id + " 이름은 : " + this.name);
    }

    @Override
    public void doSomething() {
        System.out.println("Entity 실행중..");
    }
}
