package com.note.week4;

public class thread3 {

    public static void main(String[] args) {

        int k = Thread.activeCount();

        System.out.println("k = " + k);
        // 결과 : i = 2

        // why?

        /*
        (1) main 스레드
        모든 Java 애플리케이션은 기본적으로 하나의 메인 스레드에서 시작된다.
        이 스레드는 main() 메서드를 실행한다.
        (2) 기본 데몬 스레드
        JVM은 가비지 컬렉터(Garbage Collector), JIT 컴파일러, Signal Dispatcher 같은 내부 작업을 처리하기 위해 데몬 스레드를 실행한다.
        이 데몬 스레드들은 항상 백그라운드에서 실행 중이다.

        main메서드가 스레드에서 실행되기 때문에 Thread관련 메서드가 사용 가능 한 것이다.
        */

        //#1. 객체가져오기 (currentThread()) / 쓰레드 수 (activeCount())
        Thread curThread = Thread.currentThread();
        System.out.println("현재 쓰레드의 이름 = " + curThread.getName());
        System.out.println("동작하는 쓰레스 수 = " + Thread.activeCount());


        //#2. 쓰레드 이름 지정 (자동지정)
        for(int i=0; i<3; i++) {
            Thread thread = new Thread();
            System.out.println(thread.getName());
            thread.start();
        }

        //#5. 쓰레드 수 - sout도 쓰레드이다.
        System.out.println("동작하는 쓰레스 수 = " + Thread.activeCount());

        //#3. 쓰레드 이름 직접 지정
        for(int i=0; i<3; i++) {
            Thread thread = new Thread();
            thread.setName(i+"번째 쓰레드");
            System.out.println(thread.getName());
            thread.start();
        }

        //#5. 쓰레드 수
        System.out.println("동작하는 쓰레스 수 = " + Thread.activeCount());

        //#4. 쓰레드 이름 지정 (자동지정)
        for(int i=0; i<3; i++) {
            Thread thread = new Thread();
            System.out.println(thread.getName());
            thread.start();
        }

        //#5. 쓰레드 수
        System.out.println("동작하는 쓰레스 수 = " + Thread.activeCount());

    }

}
