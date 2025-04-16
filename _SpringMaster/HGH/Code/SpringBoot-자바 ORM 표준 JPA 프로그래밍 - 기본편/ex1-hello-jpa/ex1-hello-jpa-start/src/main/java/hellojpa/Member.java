package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq") // 매핑할 데이터 베이스 시퀀스 이름
public class Member {

/*
    @Id
    private Long id;

    // unique 제약조건 설정 가능
    @Column(unique = true, length = 15)
    private String name;
    private int age;
*/
/*    // PK매핑
    @Id
    private Long id;

    // DB에 name으로 쓰고 싶을 때
    // nullable이 훨쓰면 NN 제약조건이 걸리게 된다.
    @Column(name = "name", nullable = false)
    private String username;

    // 다른 타입을 쓸 수 도있다.
    // 가장 적절한 타입이 매칭된다.
    private Integer age;

    //DB에서 ENUM을 쓰기 위한 방법
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 3가지 타입이 존재한다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // 자동으로 타입을 감지한다
    private LocalDate testLocalData;

    private LocalDateTime testLocalDataTime;

    // VARCHAR를 넘는 큰 것을 넣을때
    @Lob
    private String description;

    // 테이블 컬럼으로 저장하지 않는다.
    @Transient
    private int temp;

    // getter, setter....

    // JPA는 리플렉션을 통해 객체를 생성하기 때문에 필수적이다, 접근 제한자는 상관X
    public Member(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDate getTestLocalData() {
        return testLocalData;
    }

    public void setTestLocalData(LocalDate testLocalData) {
        this.testLocalData = testLocalData;
    }

    public LocalDateTime getTestLocalDataTime() {
        return testLocalDataTime;
    }

    public void setTestLocalDataTime(LocalDateTime testLocalDataTime) {
        this.testLocalDataTime = testLocalDataTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }*/

/*    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "member_seq_generator")
    private Long Id; // 성능에 영향을 거의 주지 않으므로 Long을 권장한다.

    private String username;

    public Member() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }*/

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
