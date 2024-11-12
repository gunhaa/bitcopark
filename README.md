
## 깊이있는 개발자가 되기 위한 스터디🔥
- 깊이있는 개발자가 될때까지 동안 진행되는 **CS & 스프링 boot 학습 & 스프링 프로젝트 리팩토링** 입니다.
- 매 주 **✔월요일 오전10:00** 까지 Jira를 통해 주어진 학습 목표를 완료하고, 광진구 1인 가구 센터에 모여서 6시간 동안 스터디를 진행합니다.
![images](/src/images/where.png)
    - Sprint의 분량을 학습 후 정리해서 질문과 답변 형식으로 스터디를 진행합니다. 
- 개발 관련 공부를 할때는 언제나 GatherTown에 접속 후 진행합니다.
    - 이슈가 있을 시 GatherTown 회의실에서 회의를 진행합니다. (마이크 on, 화면 공유on)
    - GatherTown을 통해 일일출석 체크를 진행합니다.

<br>
<br>

### 📚 STUDY
- 우아한형제들 최연소 기술이사 출신 김영한의 스프링 완전 정복 로드맵 https://www.inflearn.com/roadmaps/373

###  소스 코드 업로드 및 리뷰 요청 방법
1. Main branch에서 새 branch를 생성한다.
2. 학습한 소스 코드를 **본인의 branch**에 push한다.
3. **Pull Request**를 통해 코드 리뷰를 요청한다.
4. 스터디원에게 리뷰를 받은 후, 수정이 완료되면 Label을 수정한다.

<br>

### 🔹 Code Review 규칙
- 자유롭게 의견을 제시한다.
    - 잘했다고 생각하는 부분 칭찬하기
        - 피드백 할 게 없으면 칭찬해 주세요👍
    - 개선이 필요한 부분 설명하기
        - 단, 개선이 필요한 이유를 충분히 설명해 주세요.
        - 리뷰 과정이 숙제 검사가 아닌, 학습 과정으로 느낄 수 있게 해 주세요.
    - 궁굼한 부분 물어보기
- 오픈 커뮤니케이션 지향
    > ex) ~ 하는 게 어떨까요? / ~ 하는 것을 제안합니다. / ~ 부분은 ~ 문제가 있는 것 같은데 괜찮을까요?
- 코드 작성자에게 피드백하는 것이 아닌, 코드 자체를 피드백한다는 생각으로 리뷰한다.


<br>

### 🔹 Pull Request 규칙
- PR 템플릿에 맞게 작성한다.
    - 본인이 생각한 내용을 작성한다.
    - 코드 설명을 작성한다. (단, 주석에 작성한 경우 생략한다.)
    - 특히 리뷰를 받고 싶은 부분을 작성한다.
        - Markdown Codeblock을 이용하여 코드 일부를 발췌해서 작성한다.
        - 특히 리뷰를 받고 싶은 부분은, 리뷰어의 시간을 아낄 수 있게 본인 코드를 충분히 설명해 주세요.
- 모든 스터디원에게 리뷰를 받은 후, 코드 수정이 완료되었으면 Label을 `Merge Request`로 변경한다.
- Main branch에 병합되면, 병합된 branch는 삭제시킨다.

<br>

### 🔹 Commit Message 컨벤션
```
type : subject

body
```
#### ✔ Type
- **Add**: 소스 코드 파일 추가
- **Refactor**: 소스 코드 수정
- **Style**: 소스 코드 형식(format) 수정, 변수 네이밍 수정, 주석 추가/삭제 등 
    - (코드 동작에 영향이 없는 수정)
- **docs**: 학습 내용 정리

#### ✔ Subject
- 50자 이하의 간단한 제목을 사용한다.
    > ex) Add: 홍길동 <br>
    > ex) Refactor: MemberService 리팩토링 <br>
    > ex) Style: 함수명 변경

#### ✔ Body(optional)
- 부연 설명을 작성한다.
    > ex) Service의 코드의 관심사 분리가 가능한 것 같아서 외부 메소드로 빼냈습니다. <br>
- 한 줄에 72자를 넘기지 않는다.

<br>

### 🔹 Branch Naming 컨벤션
- `본인_이름(영어_이니셜⭕, 한글❌)`/`이슈_번호(문제_번호❌)`
    > ex) hgh/1 <br>
    - branch 이름에 한글이 들어가면 문제가 생겨서 반드시 ⭐본인 이름을 영어 이니셜⭐로 branch를 생성해 주세요!
- 각 **문제마다 branch를 새롭게 생성**해서, 소스 코드를 push 후 리뷰 요청하는 방식

<br>

### 🔹 Folder Naming 컨벤션
- `Spring{$로드맵에서의 순서}`


### 🔹 File Naming 컨벤션
- `수업에서 작성한 이름`

<br>

### 🔹 Directory 구조
```
📂 People
├── 📂Code
│   ├── 📂hello-project1
│   │   ├── 💾Example1.java
│   │   ├── 💾Example2.java
│   │   └── 💾...
│   ├── 📂hello-project2
│   │   ├── 💾Example1.java
│   │   └── 💾...
│   ├── 📂hello-project3
│   │   ├── 💾Example1.java
│   │   └── 💾...
├── 📂images(README.md)
│   ├── 📷image1.jpg
│   ├── 📷image2.png
│   └── 📷...
└── 📂md
    ├── 📂hello-project1
    │   └── 📄${topic}.md
    ├── 📂hello-project2
    │   └── 📄${topic}.md
    └── 📄{$peopleName}.md
```

<br>
<br>

## 🔸 참여자
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/gunhaa">
        <img src="https://avatars.githubusercontent.com/u/168693235?s=400&u=22d141066b86a322e82f74b4420ab00fffd98add&v=4" width="110px;" alt=""/><br />
        <sub><b>황건하</b></sub></a><br />
        <a href="https://github.com/gunhaa" title="Code">💻</a>
    </td>
    <td align="center">
      <a href="https://github.com/">
        <img src="https://avatars.githubusercontent.com/u/168693235?s=400&u=22d141066b86a322e82f74b4420ab00fffd98add&v=4" width="110px;" alt=""/><br />
        <sub><b>배지환</b></sub></a><br />
        <a href="https://github.com/" title="Code">💻</a>
    </td>
    <td align="center">
      <a href="https://github.com/">
        <img src="https://avatars.githubusercontent.com/u/168693235?s=400&u=22d141066b86a322e82f74b4420ab00fffd98add&v=4" width="110px;" alt=""/><br />
        <sub><b>이재원</b></sub></a><br />
        <a href="https://github.com/" title="Code">💻</a>
    </td>
  </tr>
</table>  
