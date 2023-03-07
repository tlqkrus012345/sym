#### Redis를 프로젝트에 적용하는 이유

- 세션 방식으로 로그인을 구현했다.
- 세션 방식은 서버에 데이터를 저장한다.
- 서버가 여러대라면 세션을 어디에 저장하는 문제와 사용자가 a 서버에서 세션을 생성했고 b 서버에 요청하는 경우 생기는 문제.
- 세션 일관성을 보장하면서 저장하기 위해 Redis를 프로젝트에 적용한다.

#### 세션 일관성을 보장하는 것이 중요한 이유

- 세션 데이터가 일관되지 않으면 웹 애플리케이션 사용자는 요청과 다른 데이터 또는 오류를 받을 수 있다.
- 세션 데이터에는 사용자의 자격 증명, 장바구니, 개인 기본 설정과 같은 정보가 포함되어 있을 수 있어 
세션 일관성을 보장하여 사용자의 개인 정보 손상을 방지한다.
- 사용자의 세션의 일관성을 유지함으로써 서버 로드와 대기 시간을 줄여 애플리케이션의 성능 개선에 도움을 줄 수 있다. 

- 세션 일관성을 보장함으로써 우리는 사용자에게 애플리케이션 성능, 보안을 개선하여 더 나은 서비스를 제공할 수 있다.

#### 세션 저장 방법

- Spring Session 또는 Apache Shiro 같은 '중앙 세션 관리 시스템'을 사용한다
    - Spring Session은 세션 관리 및 클러스터링 기능을 제공한다. 세션 클러스터링 기능을 통해 모든 서버에 동일한 세션 정보를 저장하는 것이다.
    - Apache Shiro는 세션 관리 기능을 제공하는 Java 애플리케이션용 보안 프레임워크다. 하지만 복잡하다
    - 하지만 메모리 낭비가 심하며 모든 서버에 세션을 저장하기 위한 네트워크 요청이란 비용이 발생한다.

- Redis 또는 Hazelcast 같은 '분산 캐싱 시스템'을 사용하여 세션 데이터를 저장한다
    - 자주 요청받는 데이터를 단일 서버가 아닌 클러스터의 여러 노드에 걸쳐 메모리에 저장하는 캐싱 시스템이다.
    - Redis는 세션 데이터의 낙관적 잠금 및 직렬화 모두에 사용할 수 있으므로 분산 환경에서 세션 관리를 구현하는 데 사용된다
    - 즉 외부에 레디스 서버를 구성하고 이 서버에 세션 정보를 모두 저장하고 필요시에 서버는 Redis 서버에서 세션 정보를 가져오는 것이다.
    - 데이터베이스의 로드를 줄이고 자주 요청받는 데이터에 대한 빠른 응답을 제공하여 특정 서버가 문제가 생기더라도 서비스를 지속적으로 제공할 수 있다.
    
- '로드 밸런서'를 사용하여 '고정 세션'을 지원받아 사용자 요청이 초기 요청을 제공한 동일한 애플리케이션
서버로 라우팅되로록 한다.
    - 즉 사용자의 세션이 생성된 서버로만 로드 밸런서에 의해 요청을 보내고 응답을 받는 것이다.
    - 하지만 특정 서버에만 의존하여 해당 서버에 트래픽이 집중되면 장애가 발생하고 대처가 어렵다는 문제가 있다.  

#### 세션 일관성 보장 방법

- '세션 데이터를 올바르게 직렬화'한다.
    - 세션 데이터가 직렬화되면 JSON이나 XML과 같은 형식으로 변환되며 다시 해당 데이터가 필요하면 원래 형식으로 역직렬화한다.
    - 세션에 직렬화 가능한 데이터만 저장하고 일관된 직렬화 형식을 사용한다.
    - 하지만 하나의 서버에서 직렬화 형식을 사용하고 다른 서버에서 해당 데이터를 역직렬화하는 경우 문제가 발생한다.
    - 또한 직렬화 할 수 없는 데이터를 저장할 수 없다.
  
- '낙관적 잠금 사용'하여 여러 사용자가 동일한 세션 데이터를 업데이트할 수 있는 경우에 데이터 충돌을 방지한다.
    - 많은 사용자가 동일한 데이터를 자주 업데이트하는 경우 많은 업데이트 실패가 발생하고 시스템 속도가 느려질 수 있다

- 낙관적 잠금은 세션 데이터에 대한 동시 업데이트를 관리하는 데 더 적합한 반면 세션 데이터의 직렬화는 여러 서버에서 일관된 저장 및 세션 데이터 검색을 보장하는 데 더 적합하다

#### Redis 적용하려면
- Redis 분리 아니면 단독
  - Redis 클러스터 설정 
  - 마스터 - 슬레이브 형식의 Redis
  - 캐싱용 Redis 인스턴스와 세션 저장용 Redis 인스턴스
  - Spring Session을 사용하고 세션 저장용으로 Redis
      - 나중에 다른 세션 저장소로 쉽게 변경이 가능하다.
      - Spring Security 및 기타 Spring 기반 프레임워크와 원활하게 통합된다.
      - 복잡하다
  - 등등

- Redis를 사용하여 캐싱된 데이터를 저장하기 위해서는 하나 이상의 Redis 서버를 생성해야 한다.
- 별도의 서버에 설치하거나 Docker 컨테이너 또는 클라우드 기반 서비스로 실행할 수 있다.
- Redis 서버 또는 클러스터를 설정한 후에는 Redis를 캐싱 시스템으로 사용하기 위한 설정을 해야 한다.
  (Redis 서버와 연결하는, 캐시된 데이터에 대한 CRUD, 캐시 만료 시간 및 제거 그리고 설정)

#### Redis 단일 서버 or 분리
- 기존에 Spring Session에서 캐시와 세션 저장소 모두 하나의 Redis 인스턴스에 저장되었다
단일 스레드 메모리 내 키-값 저장소로 설계하여 동일한 Redis 인스턴스에 0 ~ 16개의 데이터베이스를 만들고 사용할 수 있어서
하나의 Redis 인스턴스에서 저장이 가능했다. 
하지만 더 많은 데이터를 처리하기 위한 방법으로 캐시와 세션 저장을 위한 두 개의 Redis 인스턴스 서버를 운영하기로 했다.


- https://knight76.tistory.com/entry/redis%EC%9D%98-%EB%82%99%EA%B4%80%EC%A0%81-%EC%9E%A0%EA%B8%88optimistic-lock
- https://thenewstack.io/how-to-build-intelligence-into-your-session-stores/