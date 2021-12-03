# 내일배움캠프 프로젝트 3차
삐출사이트를 스프링으로 전환합니다.

### 🔗 라이브
https://bbichul.site/

### 🏠 소개 
<삐빅! 출석했습니다> 삐출은 출석 체크인/아웃 시간을 기록해 공부시간을 효율적으로 관리하고 팀 페이지의 추가 기능을 통해 팀 활동에 도움을 줄 수 있습니다.

### ⏲️ 개발기간  
2021년 11월 19일 금요일 ~ 2021년 12월 10일 금요일

### 🧙 맴버구성  
* 김민재  
* 김경우  
* 김성훈  
* 최대환  

### 🛠 기술스택
||프론트엔드|백엔드|
|---|---|---|
|언어|<img src="https://static.codenary.co.kr/framework_logo/javascript.png" width="30"> 자바스크립트|<img src="https://static.codenary.co.kr/framework_logo/java.png" width="30"> 자바
|프레임워크||<img src="https://static.codenary.co.kr/framework_logo/springboot.png" width="30"> 스프링부트

|데이터베이스|데브옵스|협업툴|
|---|---|---|
|<img src="https://static.codenary.co.kr/framework_logo/mysql.png" width="40"> MySQL|<img src="https://static.codenary.co.kr/framework_logo/githubaction.png" width="40"> 깃 액션|<img src="https://static.codenary.co.kr/framework_logo/slack.png" width="40"> 슬랙|
|<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FKC7de%2FbtqzYU6vFEU%2FfJuhaPvy1FSzFWNSbNw391%2Fimg.png" width="40"> H2||<img src="https://static.codenary.co.kr/framework_logo/github.png" width="40"> 깃 허브|

### 📌 브랜치관리 - <a href="https://github.com/choidaehwan/bbichul-spring/wiki#%EB%B8%8C%EB%9E%9C%EC%B9%98-%EA%B4%80%EB%A6%AC" > 상세보기 - WIKI 이동</a>

### 📌 API문서 - <a href="https://github.com/choidaehwan/bbichul-spring/wiki/API%EB%AC%B8%EC%84%9C" > 상세보기 - WIKI 이동</a>

### 📌 기술 선택 이유! - <a href="https://github.com/Dae-Hwan/BBI-CHUL-spring/wiki/%EA%B8%B0%EC%88%A0-%EC%84%A0%ED%83%9D-%EC%9D%B4%EC%9C%A0" > 상세보기 - WIKI 이동</a>

### 📌 ERD
<img width="1503" alt="스크린샷 2021-12-03 오후 7 34 10" src="https://user-images.githubusercontent.com/74276716/144588270-b9a96922-8ff0-4693-af09-35ecd7d70a23.png">


### 📌 주요 기능 - <a href="https://github.com/Dae-Hwan/BBI-CHUL-spring/wiki/%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EC%86%8C%EA%B0%9C" > 상세보기 - WIKI 이동</a>

1.체크인/아웃기능  
* count up 타이머를 이용한 체크인, 체크아웃을 통해 공부시간 측정
* 스케줄러를 이용해 24시 이후 공부시간을 일별로 나눠 저장

2.로그인기능
* bcrypt를 이용해 암호화된 회원가입 기능 
* jwt 토큰을 사용한 로그인 기능
* soft delete를 활용한 회원 탈퇴 기능

3.명언 랜덤 생성기능
* jsoup을 이용한 웹 스크래핑
* math.random함수를 이용해 명언 보여주기 

4.팀 활동 기능
* 팀 만들기 및 팀 초대 기능
* 팀원들의 실시간 출석 상황 확인
* 팀 To Do list 및 실시간 진행 상황 그래프

5.일정 확인 기능
* 개인, 팀 캘린더 추가 및 메모

6.마이 페이지 목표 달성 기능
* 목표 기간과 시간 설정 후 그래프로 달성 퍼센트 확인
* chart.js를 이용한 일별, 요일별 공부시간 확인 그래프

### 📌 문제를 이렇게 해결했어요! - <a href="https://github.com/Dae-Hwan/BBI-CHUL-spring/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85" >상세보기 - WIKI 이동</a>

### 📌 KPT회고 - <a href="https://github.com/choidaehwan/bbichul-spring/wiki/KPT-%ED%9A%8C%EA%B3%A0" >상세보기 - WIKI 이동</a>
