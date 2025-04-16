package hello.core.singleton;

public class SingletonService {

    // 싱글톤은 만들때 관례상 이런 방식으로 만들어진다.
    // 자기 자신 클래스를 호출하기 때문에 클래스 레벨에서 한개밖에 만들어지지 못한다.
    // 자바 기본 static에 대해서 추가적으로 학습하기
    // static영역에 한개만 만들어져서 올라간다.

    // 1. static 영역에 객체를 한개만 생성한다
    private static final SingletonService instance = new SingletonService();

    //2. public으로 열어 객체 인스턴스가 필요하면 static 메소드를 통해서만 조회하도록 허락한다.
    public static SingletonService getInstance(){
        return instance;
    }

    // 3. 생성자를 private로 설정해서 외부에서 new 키워드를 통한 생성을 막는다.
    private SingletonService(){
    }
    
    public void logic(){
        System.out.println("싱글톤 객체 호출중");
    }


}
