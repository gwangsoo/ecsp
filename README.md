# 로컬 개발 환경

* 참조
  * Sample : https://github.com/eventuate-tram/eventuate-tram-examples-customers-and-orders
  * CDC설정 : https://eventuate.io/docs/manual/eventuate-tram/latest/cdc-configuration.html

## Run

````
docker-compose up -d
````

| 구분       | endpoint               | id / pw                                     | 기타 |
|----------|------------------------|---------------------------------------------|----|
| kafka ui | http://localhost:3030/ |                                             |    |
| mysql    | localhost:3306         | mysqluser / mysqlpw<br/>root / rootpassword |    |
| mongodb  | localhost:27017        | root / example |    |

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

## Back-end 프로젝트 구조 및 패키지경로

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