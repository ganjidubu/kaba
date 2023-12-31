# kaba

- Java 11 사용
- Spring Boot 사용
- Gradle 사용
- h2 memory db 사용
- 외부 라이브러리 및 오픈소스 사용
  - spring-boot-starter-webflux: webclient로 외부 api 호출하고자 사용
  - lomobok: 단순 작업하지 않도록 하기위해 사용
  - spring-boot-configuration-processor: yaml파일에 작성한 프로퍼티 클래스로 사용하기 위해 사용
- API 테스트
  - 장소 검색: SearchKeyword.http
  - 검색 키워드 목록: SearchList.http

- 프로그램 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계
  - 레이어드 아키텍처에 비즈니스 로직을 도메인 레이어에 둠으로써 복잡하지 않고 추가 및 수정에 용이함
- 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 설계 및 구현 (예시. 키워드 별로 검색된 횟수)
  - 클래스를 트랜잭션을 조회와 저장에 대한 부분 나눠서 동시에 요청이 들어왔을 때 별개로 처리할 수 있도록 함
  - synchronized를 이용해서 메서드 접근을 순차적으로 할 수 있도록 했으나 단일 인스턴스때만 가능하다는 한계가 있음
- 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 코드 설계
  - 반응성을 위해서 데이터베이스 조회할 때 키워드를 검색하고 카운트를 세기 때문에 키워드에 대한 인덱스를 생성해 조회 시간을 단축시킴
- 테스트 코드를 통한 프로그램 검증 및 테스트 용이성(Testability)을 높이기 위한 코드 설계
  - controller, service, repository 에 대한 유닛 테스트를 통해 프로그램 검증
  - SearchService에서 client별 의존성을 추가하지 않고 CleintService만 의존하게끔 하여 의존성 감소로 테스트 용이해짐
- 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
  - client 패키지에 ExternalClient를 추가함으로써 새로운 장소 검색 API가 추가되었을 때 고민 없이 해당 인터페이스를 구현하게끔 설계
  - SearchService에서 client별 의존성을 추가하지 않고 CleintService만 의존하게끔 하여 변경 영역을 줄임
  - yaml 파일에 프로퍼티를 관리하여 새로운 클래스만 추가해서 프로퍼티를 사용할 수 있게끔 추가


## 나중에 확인. 필요하면 zoom 콜 가능.

- 프로그램 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계
  - 레이어드 아키텍처
    - 각 계층별 추상화 인터페이스를 두어 필요에 따라 적합한 기술 셋을 사용한 구현체를 만들 수 있도록 설계
  - search, keyword 도메인 분리
    - 관심사 분리
    - 향후 두 도메인이 별도 서비스로 분할되더라도 V2SearchEventPublisher 인터페이스의 구현체를 적절한 이벤트 큐, 메세지 브로커 기술 셋을 사용할 있도록 설계
      - 현재 도메인 사이의 결합도는 spring application context를 사용한 이벤트 처리를 통해 낮춤
      - 향후 kafka 같은 메세지 브로커를 사용한 이벤트 처리로 변경 가능
    - 이벤트 처리시 키워드 도메인의 문제가 조회 도메인의 성능이나 비즈니스 로직에 영향을 주지 않도록 비동기 처리
  - 각 도메인을 다음과 같은 기준으로 분류
    - controller - 외부 API 처리
    - service - 비즈니스 로직 처리
    - proxy - 외부 리소스 사용
    - store - 데이터베이스 사용
    - listener - 이벤트 subscriber
    - event - 이벤트 publisher
    - domain
      - controller, service, proxy, store, listener, event 도메인에서 사용하는 DTO, Entity, VO 위치
      - 위 비즈니스를 처리하는 로직들은 도메인을 기준으로 움직이며 자신의 하위 레이어와 도메인 패키지에만 의존함(각 파일의 import 문을 통해 확인 가능)
      - 순환 의존성이 발생하지 않음

- 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 설계 및 구현 (예시. 키워드 별로 검색된 횟수)
  - jpa insert 처리 시 generate 전략을 identity로 설정
    - 원천 데이터 소스에서 키워드 이력을 쌓을 때 데이터가 중복되지 않도록 설계

- 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 코드 설계
  - immutable 객체만 사용 - thread safe 한 구현
  - 각 빈들을 stateless 하게 설계 및 구현
  - search 도메인과 keyword 도메인 사이 이벤트를 비동기 처리
  - KeywordEntity 쿼리 인덱스 추가

- 테스트 코드를 통한 프로그램 검증 및 테스트 용이성(Testability)을 높이기 위한 코드 설계
  - 각 컴포넌트 별 단위 테스트
    - 비즈니스 로직에 대한 빠른 검증과 피드백
  - 스프링 프레임워크의 기능이 필요한 경우 결합 테스트
    - 스프링 컨텍스트 로딩을 최소한하기 위해 @DataJpaTest, @WebMvcTest 사용
  - API 요청과 응답에 대한 검증을 위해 wire mock 사용

- 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
  - 각 API 제공자 별 FeignClient 사용
    - 인터페이스로 추상화 되어 있으며 향후 API 요청을 위해 다른 기술 셋을 사용한다면 필요한 구현체를 만들어 사용하기 용이
  - 필요한 token, sceret을 동적으로 사용할 수 있는 configuration bean 사용 