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

