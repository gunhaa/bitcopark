
## 깊이있는 개발자가 되기 위한 스터디🔥

- 깊이있는 개발자가 될때까지 동안 진행되는 **CS & Spring boot 학습 & Spring MVC 프로젝트 리팩토링** 입니다.
- 매 주 **✔월요일 오전10:00** 까지 Jira를 통해 주어진 학습 목표를 완료하고, 광진구 1인 가구 센터에 모여서 5~6시간 동안 스터디를 진행합니다.
    - Sprint의 분량을 학습 후 정리해서 질문과 답변 형식으로 스터디를 진행합니다. 

<img src="/src/images/where.png" alt="Refactoring" width="500" height="400">

- 개발 관련 공부를 할때는 GatherTown에 접속 후 진행합니다.
    - 이슈가 있을 시 GatherTown 회의실에서 회의를 진행합니다.
    - GatherTown을 통해 일일출석 체크를 진행합니다.

<br>
<br>

### 📚 STUDY
- 김영한의 스프링 완전 정복
- CS 베스트셀러
- Spring boot
- JPA
- Docker
- CI/CD
- linux
- AWS

###  소스 코드 업로드
1. Main branch에서 새 branch를 생성한다.
2. 학습한 내용을 **본인의 branch**에 push한다.
3. 스터디에서 학습을 진행한 후, main에 pr한다.

<br>


### CS 관련 책 읽기(12월)
<img src="/src/images/Refactoring.png" alt="Refactoring" width="300" height="400">

- 읽어야 할 책 : 리팩터링
- 읽은 후 기존 프로젝트 리팩토링 작업 시작

<br>


### 🔹 Code Review 요청
- 새로운 브랜치를 생성후 학습한 내용을 바탕으로 리팩토링 후 코드 리뷰를 요청한다. 
- 자유롭게 의견을 제시한다.
    - 잘했다고 생각하는 부분 칭찬하기
    - 개선이 필요한 부분 설명하기
        - 단, 개선이 필요한 이유를 충분히 설명해 주세요.
    - 궁굼한 부분 물어보기
- 코드 작성자에게 피드백하는 것이 아닌, 코드 자체를 피드백한다는 생각으로 리뷰한다.


<br>

### 🔹 Pull Request 규칙
- 스터디에서 발표한 주제를 정리한 후 PR한다.
- 스터디에서 진행한 내용의 Branch를 병합시킨다.

<br>

### 🔹 Commit Message 컨벤션

```
type : subject
```

#### ✔ Type
- **Add**: 소스 코드 파일 추가
- **Refactor**: 소스 코드 수정
- **chore** : 기타 변경 사항
- **docs**: md 내용 변경

#### ✔ Subject
- 50자 이하의 간단한 제목을 사용한다.
    > ex) Add: 홍길동 <br>
    > ex) Refactor: MemberService 리팩토링 <br>
    > ex) Style: 함수명 변경


<br>

### 🔹 Branch 컨벤션
- `본인_이름(영어_이니셜⭕)`/`숫자(n주차)`
    > ex) hgh/1 <br>
    - branch 이름에 한글이 들어가면 문제가 생겨서 반드시 본인 이름을 영어 이니셜로 branch를 생성해 주세요!
- 각 주마다 branch를 새롭게 생성해서 정리한다.
- 코드 리뷰 요청할 브랜치의 경우 `본인_이름(영어_이니셜⭕) / review${숫자}` 로 생성한다.

<br>

### 🔹 Folder 컨벤션
- `기술스택-{$강의이름}` / 특정 강의 내용 정리
- 각 주차 정리 내용은 Recap폴더 안에 `Week{n}` 으로 작성한다


<br>

### 🔹 Directory 구조 예제
```
📂 People
├── 📂Code
│   ├── 📂SpringBoot - 실전! 스프링부트1
│   │   ├── 💾Example1.java
│   │   ├── 💾Example2.java
│   │   └── 💾...
│   ├── 📂SpringBoot - 자바 ORM 표준 JPA
│   │   ├── 💾Example1.java
│   │   └── 💾...
│   ├── 📂Week1
│   │   ├── 💾Example1.java
│   │   └── 💾...
├── 📂images(README.md)
│   ├── 📷image1.jpg
│   ├── 📷image2.png
│   └── 📷...
└── 📂md
│   ├── 📂SpringBoot - 실전! 스프링부트1
│   │   └── 📄${topic1}.md
│   │   └── 📄${topic2}.md
│   └── 📄{$peopleName}.md
│
└── 📂Recap
    └── 📄${주차별 스터디 요약}.md
```

<br>
<br>

## 🔸 참여자
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/gunhaa">
        <img src="https://avatars.githubusercontent.com/u/168693235?s=400&u=22d141066b86a322e82f74b4420ab00fffd98add&v=4" width="110px;" height="90px" alt=""/><br />
        <sub><b>황건하</b></sub></a><br />
        <a href="https://github.com/gunhaa" title="Code">💻</a>
    </td>
    <td align="center">
      <a href="https://github.com/">
        <img src="https://mud-kage.kakaocdn.net/dn/ddUiy9/btsdGABygpb/MsARp4M5vZdcumFmyHKoN1/c360.jpg" width="110px" height="90px" alt=""/>
        <br />
        <sub><b>배지환</b></sub></a><br />
        <a href="https://github.com/" title="Code">💻</a>
    </td>
    <td align="center">
      <a href="https://github.com/">
        <img src="https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/cxuc/image/ouT5pWpIm1C2I8RVqlBhspj_qQA.jpg" width="110px;" height="90px" alt=""/><br />
        <sub><b>이재원</b></sub></a><br />
        <a href="https://github.com/" title="Code">💻</a>
    </td>
  </tr>
</table>  
