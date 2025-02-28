# OS

- [OS란?](https://github.com/gunhaa/CS-Study-Repo/blob/main/CS/OS/OperationSystem.md)
- [OS란?2](https://github.com/gunhaa/CS-Study-Repo/blob/main/CS/OS/NullNull/OS.md)
- [프로세스](https://github.com/gunhaa/CS-Study-Repo/blob/main/CS/OS/ChoiLyn/Operation&Process.md)
  - [프로세스](https://github.com/gunhaa/CS-Study-Repo/blob/main/CS/OS/ChoiLyn/Process.md)

## 컴퓨터란?
- 컴퓨터는 튜링완전해야한다
  - 튜링 완전한 컴퓨터는 튜링 머신이 수행할 수 있는 모든 계산을 실행할 수 있어야 한다
    - 튜링머신은 모든 계산 가능한 문제를 해결할 수 있는 이론적 장치이다
    - 바이너리를 읽는 가상의 기계이다(현대 컴퓨터)
  - 이를 위해 기본적인 연산과 제어 구조가 가능해야 한다
    - for while(연산수행)/ if else(분기제어)가 가능해야한다
  - 컴퓨터는 알고리즘을 이용해 모든 것을 계산 할 수 있어야 한다
  - 데이터를 저장하고 참조할 수 있는 메모리가 필요하다
## OS란?
- OS란 I/O를 관리하는 하나의 시스템
- OS는 튜링완전한 계산기인 컴퓨터를 다루기 쉽게 관리하는 소프트웨어다.
- 이를 Process, Process Controll Block, Thread, 동시성제어,파일 시스템, 메모리관리, 입출력관리를 통해서 구현한다
  - 프로세스 관리(Process Management)
    - 프로세스를 생성, 실행, 종료하며 CPU를 효율적으로 분배한다.
    - PCB를 이용해 프로세스의 상태를 관리한다.
  - 스레드 및 동시성 제어
    - 하나의 프로세스 내에서 여러 개의 작업(스레드)을 수행할 수 있도록 한다.
    - 멀티스레딩, 동기화, 상호 배제(뮤텍스), 세마포어 등을 활용하여 병렬 처리를 효율적으로 수행한다.
  - 메모리 관리
     - 프로그램이 실행될 때 필요한 메모리를 할당하고 해제한다.
     - 가상 메모리, 페이지 교체 알고리즘, 캐시 메모리 등을 통해 성능을 최적화한다.
  - 파일 시스템 관리
     - 데이터를 저장하고 불러올 수 있도록 디렉터리, 파일 읽기/쓰기, 접근 권한 등을 관리한다.
  - 입출력(I/O) 관리
    - 키보드, 마우스, 디스크, 네트워크 등의 장치와 데이터를 주고받는 기능을 제공한다.