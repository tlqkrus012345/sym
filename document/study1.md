- @Id 와 @GeneratedValue

```markdown
@Id : 데이터베이스 테이블의 기본 키(PK)와 객체의 필드를 매핑시켜주는 어노테이션이다.
@GeneratedValue : 기본 키를 자동 생성해주는 어노테이션이다.

@GeneratedValue의 strategy의 기본값은 AUTO이다.

데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택한다.

IDENTITY 전략은 기본 키 생성을 데이터베이스에 위임하는 전략이다.

SEQUENCE 전략은 이 시퀀스를 사용해서 기본 키를 생성한다.

TABLE 전략은 키 생성 전용 테이블을 하나 만들고, 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스를 흉내내는 전략이다.
```

- 모든 설계에 인터페이스를 구현해야 하는가?

```markdown
우리는 인터페이스와 구현체의 분리를 통해 클라이언트의 코드 변경 없이 기능을 확장할 수 있는 이점이 있다.

하지만 인터페이스를 도입하면 추상화라는 비용이 발생한다.

기능을 확장할 가능성이 없다면, 구체 클래스를 직접 사용하고, 향후 꼭 필요할 때 리팩터링해서 인터페이스를 도입하는 것도 방법이다.
```

- 동시성 이슈 ? HashMap vs ConcurrentHashMap

```markdown
동시성 이슈란 여러 스레드가 동시에 같은 인스턴스의 필드 값을 변경하면서 발생하는 문제를 의미한다.

주요 차이점 은 첫 번째는 읽기에 대한 전체 동시성과 쓰기에 대한 높은 동시성을 구현한다는 것이다.

다른 스레드가 같은 맵 키에 쓰기 접근을 허용하지 않는다. 반면에 읽는 접근은 허용한다.

HashMap은 thread-safe하지 않아, 싱글 쓰레드 환경에서 사용하는 게 좋다

ConcurrentHashMap은 thread-safe하기 때문에, 멀티 쓰레드 환경에서 사용할 수 있다
```

- Optional ?

(https://github.com/tlqkrus012345/note/blob/master/java/optional.md)

- Gitflow 와 Githubflow

```markdown
gitflow: master, develop, feature, release, hotfix 브랜치를 설정하고 운영하는 방식

github flow: main(master), feature 브랜치만으로 운영하는 방식
```