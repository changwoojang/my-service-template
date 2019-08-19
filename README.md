# kakaopay project
카카오페이 사전 과제
    - 서버개발_사전과제3_주택금융API개발_v.1.1
   
## 필수 요구 사항 
- [x] 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
- [x] 주택금융 공급 금융기관(은행) 목록을 출력하는 API 개발
- [x] 년도별 각 금융기관의 지원금액 합계를 출력하는 API 개발
- [x] 각 년도 별 각기관의 전체 지원 금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
- [x] 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
 
 ## 선택 요구 사항
- API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 인증 기능을 개발하고 각 API 호출 시에 HTTP Header 에 발급받은 토큰을 가지고 호출
- [x] signup 계정생성 API: 입력으로 ID, PW 받아 내부 DB 에 계정 저장하고 토큰 생성하여 출력
- [x] 패스워드는 인코딩하여 저장한다.
- [x] 토큰은 특정 secret 으로 서명하여 생성한다.
- [x] signin 로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면
      토큰을 발급한다.
- [x] refresh 토큰 재발급 API: 기존에 발급받은 토큰을 Authorization 헤더에
      “Bearer Token”으로 입력 요청을 하면 토큰을 재발급한다.

- 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 개발
-[x] 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측
 ## 개발 프레임워크
    - Spring Boot
        - spring-boot-starter-data-jpa
        - spring-boot-starter-web
    - Java8, H2 
    - MockMvc, Lombok, Jackson
    
 ## 문제 해결 전략
 
    1. Lombok을 사용하여 보일러플레이트 코드를 줄인다.
    2. Spring web starter에 포함되어 있는 jackson-databind를 사용하여 json형태로 결과값을 return해준다.
    3. csv파일 업로드 
            - 파일 contents내에 한글이 포함되어 있으므로 EUC-KR로 인코딩/디코딩을 해준다.
            - 파일 업로드 시에 필요한 데이터를 parsing하여 Entity를 구성한다.
                - BankEntity : 기관 명과 기관 코드를 저장
                - SupplyAmount : 파일에 포함된 모든 내용을 저장한다.
                - SupplyAmountSum : 년도별로 각 기관들의 총 합계를 저장해둔다.
                    - 평균값 및 합계 API에서 공통적으로 사용된다.
                    - 미리 연산을 해둔 상태이기 때문에 불필요한 연산을 제거할수가 있다.  
            - Validation 사항
                - 지원하지 않는 파일 확장자를 업로드 할 경우
    4. "외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API" 에서는 2017년도를 제외한 결과를 보여줘야 하므로 주의한다.
    5. @Controller, @Service, @Repository Annotation별로 레이어를 분리해서 각 annotation에 맞게 역할을 하다록한다.
        - Controller : 비즈니스 로직은 포함하지 않고 단순히 어떤 Service레이어로 요청을 보낼지 정의하고 Request/Response에 대한 validation을 한다.
        - Service : 비즈니스 로직만을 정의한다.
        - Repository : DB에 접근하는 레이어로 JPQL, DAO를 정의한다. 
    6. ERROR CODE는 아래와 같이 정의한다.
        - ENUM타입으로 code, HttpStatus그리고 Messsage로 정의한다.
        - BAD_REQUEST("N9000", HttpStatus.BAD_REQUEST, "잘못된 요청입니다.")
        - FORBIDDEN_ACCESS("N9010", HttpStatus.BAD_REQUEST, "해당 정보로 접근이 불가능합니다.")
        - FILE_EXTENSION_NOT_FOUND("F0001", HttpStatus.BAD_REQUEST,"잘못된 확장자의 파일입니다.")
        - FILE_EMPTY("F0002",HttpStatus.BAD_REQUEST,"파일이 비어있습니다.")
        - PERIOD_NOT_FOUND("B0001", HttpStatus.BAD_REQUEST,"지원하지 않는 기간 단위입니다.")
        - BANK_NOT_FOUND("B0002", HttpStatus.BAD_REQUEST,"존재하지 않는 금융기관입니다.")
    7. Request/Reponse시에 필요한 데이터는 DTO를 통해서 전달한다.
    8. 데이터에 대한 연산은 최대한 Stream을 사용하여 간결하고 보기 쉽도록 작성한다.
        - 보기 어려운 코드는 재사용성이 떨어진다.
    9. 중복된 기능을 하는 function이 없도록 한다.
    10. 간결성을 위해서 보일러플레이트 코드가 없도록 한다.
    11. 일관성 있는 API 설계를 하자.
        - URI은 가독성을 위해서 하이픈(-)을 사용한다.
        - URI는 정보의 자원을 표현해야 하고 동사보다는 명사를 사용해서 정한다.
    12. 테스트는 MockMvc를 사용한다. 
        - json 형태의 validation이 가능하여 생산성이 높다.
        - Application Context 를 load해서 테스트한다. 
    13. API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 인증 기능
        - 비밀번호 암복호화는 AES_256
            - JAVA의 기본 정책으로 AES128 암호화 방식까지만 사용 가능하다. 그래서 AES256 방식으로 암호화하게 되면 아래와 같은 Exceptioin이 발생한다.
            - 해결 방법은 아래와 같다.
            - Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy
            - JDK 1.8u151 버전이후 부터는 crypto.policy 프로퍼티 수정. (limited -> unlimited)
            - 비밀번호 암호화만 필요하다면 SHA-256를 사용한다.
        - 해당 프로젝트는 JAVA 설정 밑 교체가 필요 없는 SHA-256를 통해서 복호화가 불가능한 단방향 암호화를 사용한다.
        - 중복 가입을 방지
            - controller단 validation과 Entity unique annotation 설정 
       
## 빌드 및 실행 방법
    1. gradle build && java -jar build/libs/project-0.0.1-SNAPSHOT.jar
    2. 계정생성한다. (/v1/bank/member/signup)
    3. 로그인을 해서 토큰을 발급받는다. (/v1/bank/member/login)
    4. 모든 요청의 헤더에"Authorization" key값으로 발급 받은 토큰을 와 value를 "Bearer "뒤에 넣어준다.
    5. 데이터 파일을 업로드하여 기초 데이터를 저장한다. "데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API" 
        1) /v1/file/upload
    6. 기초 데이터를 성공적으로 업로드 했다면 API 명세서의 API를 자유롭게 요청을 하면 된다.
      
    
## API 명세서

URI|request|response|description
---|---|---|---
/v1/bank/member/signup |POST ?memberId=chang&password=init_password|200,{"memberId": "changwoo"} | 계정을 생성한다. 비밀번호는 SHA-256암호화
/v1/bank/member/login |POST ?memberId=chang&password=init_password|200,{"memberId": "changwoo","token":"qwivn21..."} | 로그인을 하여 토큰을 발급받는다.
/v1/bank/member/token-refresh|GET header(Authorization="Bearer ")|200,{"memberId": "changwoo","token":"qwivn21..."}| 토큰을 갱신한다|
/v1/file/upload|POST header(Authorization="Bearer ") content-type = "multipart/form-data" , 확장자 csv  |200, {"fileType": "text/csv","fileRecordCount": 154,"fileByteSize": 8822}| 이터 파일에서 각 레코드를 데이터베이스에 저장
/v1/bank/list|GET  header(Authorization="Bearer ")|200, {"name": "주택금융 공급 금융기관(은행) 목록","data": [{"institute_name": "국민은행","institute_code": "bnk1111"},...]}| 주택금융 공급 금융기관(은행) 목록을 출력
/v1/bank/supply-amount/top |GET header(Authorization="Bearer ")|200, {"year":"2016","bank":"기타은행"}|  각 년도 별 각기관의 전체 지원 금액 중에서 가장 큰 금액의 기관명을 출력
/v1/bank/supply-amount/total |GET header(Authorization="Bearer ") ?period=year |200, {"name" : "주택금융 공급현황",[ {"year" : "2005","total_amount" : 46201,….}},….]}| 년도별 각 금융기관의 지원금액 합계를 출력
/v1/bank/supply-amount/average |GET header(Authorization="Bearer ") ?bankName=KEB_BANK |200, {"bank":"외환은행","support_amount":[{"year":"2008","amount":78.0},{"year":"2015","amount":1702.0}]} | 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력

> 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API  

URL 및 HTTP Method

| POST /v1/file/upload ||
|--------|-----|
| `Request Content-type` | `multipart/form-data`|
|`Response Content-Type` | `application/json`|

Request Parameters

| Name | Type | Required | Description |
|------|------|----------|-------------|
| `file` | multiform/part | Y | 데이터 파일 | 

Response Object Fields

| Name | Type | Required | Description |
|-------|-------|-------|------------|
| `fileType` | string | Y | 데이터 파일 |
| `fileRecordCount` | integer | Y | 데이터 파일 |
| `fileByteSize` | integer | Y | 데이터 파일 |

> 주택금융 공급 금융기관(은행) 목록을 출력하는 API 

URL 및 HTTP Method

| GET /v1/bank/list ||
|--------|-----|
| `Request Content-type` | N/A|
| `Response Content-Type` |application/json|

Request Parameters

| Name | Type | Required | Description |
|------|------|----------|-------------|
| N/A | N/A  | N | N/A  | 

Response Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `name` | string | Y | 이름 |
| `data` | Bank[] | Y | 데이터 |

Bank Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `institute_name` | string | N | 기관 이름 |
| `institute_code` | string | N | 기관 코드 |

> 년도별 각 금융기관의 지원금액 합계를 출력하는 API 

URL 및 HTTP Method

| GET /v1/bank/total/supply-amount ||
|--------|-----|
| `Request Content-type` | N/A|
| `Response Content-Type` |text/plain|

Request Parameters

| Name | Type | Required | Description |
|------|------|----------|-------------|
| `period`| string  | Y | 기간 단위 (ex : year) | 

Response Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `name` | string | N | 이름 |
|  | `SupplyAmountSumTotal` | N | 지원금액의 합계 |

SupplyAmountSumTotal Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `year` | string | N | 년도 |
| `total_amount` | string | N | 각 기관별 총 합계 |
| `detail_amount` | `SupplyAmountSum`[] | N | 각 기관별 공급 금액의 목록|

SupplyAmountSum Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `주택도시기금` | integer | N | 주택도시기금 공급 금액 |
| `국민은행` | integer | N | 국민은행 공급 금액 |
| `우리은행` | integer | N | 우리은행 공급 금액 |
| `한국시티은행` | integer | N | 한국시티은행 공급 금액 |
| `신한은행` | integer | N | 신한은행 공급 금액 |
| `농협은행/수협은행` | integer | N | 농협은행/수협은행 공급 금액 |
| `외환은행` | integer | N | 외환은행 공급 금액 |
| `기타은행` | integer | N | 기타은행 공급 금액 |
| `하나은행` | integer | N | 하나은행 공급 금액 |

> 각 년도 별 각기관의 전체 지원 금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발

URL 및 HTTP Method

| GET /v1/bank/top/supply-amount ||
|--------|-----|
| `Request Content-type` | N/A|
| `Response Content-Type` |`application/json`|

Request Parameters

| Name | Type | Required | Description |
|------|------|----------|-------------|
| `N/A`| `N/A`  | `N/A` | `N/A` | 

Response Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `year` | string | N | 년도 |
| `bank` | string | N | 기관 이름 |

> 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발

URL 및 HTTP Method

| POST /v1/bank/total/supply-amount/average ||
|--------|-----|
| `Request Content-type` | N/A|
| `Response Content-Type` |`application/json`|

Request Parameters

| Name | Type | Required | Description |
|------|------|----------|-------------|
| `bankName` | string  | Y | 각 기관 값 ( `KB_BANK`, `KEB_BANK`,`SHINHAN_BANK`, `WOORI_BANK`, `CITY_BANK`, `HANA_BANK`,`NONGHYUP_BANK` , `ETC_BANK`)| 

Response Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `bank` | string | N | 기관 이름 |
| `support_amount` | `SupplyAmountAverageMinMax` | N | 지원금액 평균 중에서 가장 작은 금액과 큰 금액 |

SupplyAmountAverageMinMax Object Fields

| Name | Type | Nullable | Description |
|-------|-------|-------|------------|
| `year` | integer | N | 년도 |
| `amount` | integer | N | 공급 금액 |




