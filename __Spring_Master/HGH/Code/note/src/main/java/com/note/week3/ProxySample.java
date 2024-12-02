package com.note.week3;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProxySample implements ParentInterface {

    private Class<?> originalClazz;
    private Object originalInstance;
//    private List<?> fields;
    private Long id;
    private String name;


    public ProxySample(Class<?> proxyClazz, Long id , String name) {
        this.originalClazz = proxyClazz;
//        this.fields = fields;
        this.id = id;
        this.name = name;
    }

    public void print(){
                    // 기본 생성자를 통해 인스턴스를 만들어낸다
        try {

            // type narrowing 필요
            originalInstance = originalClazz.getDeclaredConstructor().newInstance();
            // 메소드 호출 오류 처리 필요
            // 동적으로 필드 배열을 가져와서 처리해서 이쁘게 리팩토링 가능
            Field idField = originalClazz.getDeclaredField("id");
            Field nameField = originalClazz.getDeclaredField("name");

            // 이런식으로 동적 처리 가능
            Class<?> idType = idField.getType();
            Class<?> nameType = nameField.getType();

            // 여기도 동적으로 메소드배열 가져와서 이쁘게 처리 가능
            Method setId = originalClazz.getMethod("setId", idType);
            Method setName = originalClazz.getMethod("setName", nameType);

            setId.invoke(originalInstance ,  this.id);
            setName.invoke(originalInstance,  this.name);

            // 여기도
            Method printMethod = originalClazz.getMethod("print");

            printMethod.invoke(originalInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    @Override
    public void doSomething() {
        System.out.println("Proxy 실행중..");
    }
}
