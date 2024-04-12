# 로컬 개발 환경

* 참조
  * Sample
    * https://github.com/eventuate-tram/eventuate-tram-examples-customers-and-orders
    * https://github.com/microservices-patterns/ftgo-application
  * CDC설정 : https://eventuate.io/docs/manual/eventuate-tram/latest/cdc-configuration.html
  * Schema
    * mysql
      * common : https://github.com/eventuate-foundation/eventuate-common/tree/master/mysql
      * saga : https://github.com/eventuate-tram/eventuate-tram-sagas/mysql

## Run

* 실행중인 docker-compose container 를 내리고 삭제한다.
  ````
  docker-compose down -v
  ````
* 형상이 변경된 image가 있을 경우 다시 빌드한다.
  ````
  docker-compose build
  ````
* docker-compose 를 실행한다.
  ````
  docker-compose up -d
  ````
* docker-compose 를 실행한다.
  ````
  docker-compose up -d
  ````
* docker-compose 실행여부를 확인한다.
  ````
  docker-compose ps -a
  docker-compose stats
  ````
* docker-compose 로그를 확인한다.
  ````
  docker-compose logs -f
  docker-compose logs -f <서비스명>
  ````

| 구분           | endpoint                                                   | id / pw                                     | 기타                                                                                    |
|--------------|------------------------------------------------------------|---------------------------------------------|---------------------------------------------------------------------------------------|
| kafka ui     | http://localhost:3030/                                     |                                             |                                                                                       |
| mysql        | localhost:3306                                             | mysqluser / mysqlpw<br/>root / rootpassword |                                                                                       |
| mongodb      | localhost:27017                                            | root / example |                                                                                       |
| Zipkin       | http://localhost:9411/zipkin/                              |  | service tracing                                                                       |
| AbcService   | http://localhost:8082/services/abc/swagger-ui/index.html   |  | post method가 호출되면 Abc 등록 후 Xyz도 등록 Command를 호출한다.                                     |
| XyzService   | http://localhost:8083/services/xyz/swagger-ui/index.html   |  |                                                                                       |
| OrderService | http://localhost:8084/services/order/swagger-ui/index.html |  | post method가 호출되면 order를 등록 후 SAGA패턴을 사용하여 Abc,Xyz 서비스를 호출하여 등록하고 완료시 order 상태를 변경한다. |

## Back-end

### versions

* Java : JDK 17 LTS
* SpringBoot : 3.2.4
* SpringCloud : 2023.0.1
* eventuateTram : 0.34.0.RELEASE
* eventuateSaga : 0.23.0.RELEASE
* resilience4j : 2.2.0
* openfeign : 13.2
* openapi : 3.0.0
* mapstruct : 1.5.5.Final
* springdoc-openapi-webmvc : 2.2.0

### Back-end 프로젝트 구조 및 패키지경로

* xxxxx Project : com.hae.ecsp.xxxxx
  * sub projtect : xxxxx-share
    * domain
      * dto
      * entity
    * eventuate
      * command
      * event
  * sub project : xxxxx-main
    * client
    * config
    * eventuate
    * repository
    * security
    * service
      * impl
      * mapper
    * util
    * web
      * rest
      * websocket

### Build

* common
  * common build
    ````
    ./gradlew jar
    ````
  * common publishing
    ````
    ./gradlew publishToMavenLocal
    ````
* share module build
  * build
    ````
    ./gradlew jar
    ````
  * publishing
    ````
    ./gradlew publishToMavenLocal
    ````
* main module build
  * build
    ````
    ./gradlew bootJar
    ````
  * run
    ````
    ./gradlew bootRun
    ````

### TODO

* ~~eventaute saga 패턴 샘플~~
* spring security 권한 샘플
* ~~resilience4j 샘플~~
* 몽고DB 연계 샘플
~~* zipkin / sleuth~~
  * io.eventuate.tram.springcloudsleuth:eventuate-tram-spring-cloud-sleuth-tram-common:0.4.0.RELEASE 버전이 맞지않아 적용되지 않으나, 향후 적용 예정.
* kafka-connect
