package com.note.week4;

public class thread4 {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        Thread thread = Thread.currentThread();

        System.out.println("thread.getPriority() = " + thread.getPriority());
        
        for(int i=0; i<10; i++){

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(long i =0; i<1000000000; i++){}
                    System.out.println("Thread 이름 = " + Thread.currentThread().getName() + "우선 순위 : " + Thread.currentThread().getPriority());
                }
            });

            thread2.setName(i + "번째 쓰레드");

            if(i==0){
                thread2.setPriority(Thread.MAX_PRIORITY);
            }
            thread2.start();
        }
        
        
        
    }

}
