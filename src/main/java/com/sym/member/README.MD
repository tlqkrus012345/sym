## Member Entity
### @Id
- 데이터베이스 테이블의 기본 키(PK)와 객체의 필드를 매핑시켜주는 어노테이션이다.
### @GeneratedValue 
- 기본 키를 자동 생성해주는 어노테이션이다.
- @GeneratedValue의 strategy의 기본값은 AUTO이다.
  - 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택한다.
- IDENTITY 전략은 기본 키 생성을 데이터베이스에 위임하는 전략이다.
- SEQUENCE 전략은 이 시퀀스를 사용해서 기본 키를 생성한다.
- TABLE 전략은 키 생성 전용 테이블을 하나 만들고, 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스를 흉내내는 전략이다.

---

