package hello.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// localhost:8080/test
@WebServlet(urlPatterns = "/test")
public class TestServlet extends HttpServlet {

    // ./gradlew build 를통해 빌드하고, build/libs/ 의 war파일을 톰캣에 배포해야 한다.
    // war 압축풀기 , jar -xvf server-0.0.1-SNAPSHOT.war
    // JAR이 JVM위에서 실행된다면, WAR는 웹 어플리케이션 서버 위에서 실행된다.
    // HTML과 같은 static파일과 클래스파일을 모두 포함한다. Jar에 비해서 더 복잡함

    // build한 war파일을 톰캣의 /webapps 폴더로 이동시킨후 이름을 ROOT로 지정하고, 톰캣을 치면 들어갈 수 있다.
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("TestServlet.service");
        resp.getWriter().println("test");
    }

}
