package com.note.week3;

public class RuntimeClazz {

    public static void main(String[] args) {


        // 프록시 객체 생성
        // 생성시에는 최소한의 자원으로 객체를 만들고
        // 프록시의 print메소드 호출 시 원래 클래스의 필드값을 반환

        ProxySample proxy = new ProxySample(Entity.class, 1L, "ReflectionProxySample");
        proxy.print(); // entity 클래스의 값 반환

    }
}
