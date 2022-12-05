# 🍎 파인드애플(FindApple) Backend

<br>

## ✏ 프로젝트 소개
- [서비스 목적]  중고거래 사이트 가격 책정 한계 개선(불합리한 가격 제안 방지)
- 애플 제품 군에 특화된 옵션들에 맞춰 고도화된 카테고리를 제공합니다.
- 애플 중고 얼마에 팔아야 할지 고민 되시나요?
- 파인드애플은 애플 중고 물품 가격을 산정해드립니다.
- 시세에 맞는 물품들을 안심하고 구매하세요!
- 책정된 가격이 마음에 들지 않는다면 이의제기를 통해 유저들과 의견을 나눠볼 수 있습니다.

<br>

## ⭐ 주요 기능
- 🍫카카오톡 소셜 로그인
- ♾무한 스크롤 
- 🔎검색 및 조회 기능
- 🙋‍마이 페이지에서는 내가 올린 게시글도 모아 보고 내가 좋아한 게시글도 모아서 확인
- 💬(예정) WebSocket을 이용한 실시간 채팅 및 알림

<br>

## 👥 Backend 팀원 소개


 | Name | Github | Position|
| --- | --- | --- |
| 김정수 | [https://github.com/dnjawm19](https://github.com/dnjawm19) | 부팀장  |
| 이승주 | [https://github.com/RefinedStone](https://github.com/RefinedStone)  | 팀원 |
| 백나윤 | [https://github.com/NayoonBaek](https://github.com/NayoonBaek) | 팀원 |
| 김재경 | [https://github.com/machdd365](https://github.com/machdd365) | 팀원 |

<br>

## 🗓 프로젝트 기간
- 2022년 11월 07일 ~

<br>

## 📜 아키텍쳐 
<details><summary>아키텍쳐 보기
</summary>

![image](https://user-images.githubusercontent.com/113869496/203765586-d0e682c5-5c58-409c-9dd9-757be83c7c67.png)

</details>

<br>



## 🛠 Backend Tech Stack

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"> <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">
<br>
<img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white">
<img src="https://img.shields.io/badge/Sourcetree-0052CC?style=for-the-badge&logo=Sourcetree&logoColor=white">
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white">
<br>
<img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white">
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/kakao login-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">
<br>

## 📔 LINK
- 노션링크 :  [https://www.notion.so/1-2048c4aab357410e922fe426d5c24c99](https://www.notion.so/1-2048c4aab357410e922fe426d5c24c99)   



<br>

## 🚀 Troubleshooting
- ### 1. S3로 올린 image가 일시적으로 엑스박스로 뜨는 현상
![image](https://user-images.githubusercontent.com/113455892/203907087-6d605b3a-e6ce-4637-b272-1f153f90fce1.png)
![image](https://user-images.githubusercontent.com/113455892/203907069-cdfb515e-223d-4555-b6d6-001d994c379c.png)
![image](https://user-images.githubusercontent.com/113455892/203907097-80924175-64c0-49fc-8be6-d5a0cf65c67f.png)
![image](https://user-images.githubusercontent.com/113455892/203907108-9f4676dc-bf48-4acf-91ad-48610a5ad224.png)

aws s3로 올린 이미지가 저렇게 엑스박스로 뜨는 경우가 있습니다.
액스박스에 우클릭-새탭에서 이미지 열기를 하면 이미지와 같이 연결이 비공개로 설정되어 있지 않다는 메시지가 뜹니다.
 - 안전하지않음 으로 이동을 딱 '1'번만 해주면 그 브라우저에서는 정상적으로 계속 사진이 나옵니다.
pc를 껏다 켜도 계속 정상적으로 나옵니다.
이 문제에 대해서 저는 2가지가 문제일 것이라고 생각을 했습니다.


 1.https 인증서 문제
 
 
 
 2. aws s3 설정 문제
 
 
1의 경우에는 인증서를 새로 만들어서 도메인을 팠는데도(백엔드 서버)  문제가 해결 되지 않았습니다.
2의 경우에는 왠만한 모든옵션을 public와 access가 전부 가능하게 임시적으로 변경했음에도 고쳐지지가 않습니다..
<br>

>>>>>문제해결 방법


aws s3의 bucket 네이밍 규칙에 .와 -를 사용 할 수 있다고 되어 있지만, 실질적으로 .과 -를 사용하지 않고 다시 버킷을 만들었더니 해결이 되었씁니다..



## 🎈 ERD
<details><summary>ERD 보기
</summary>

![image](https://user-images.githubusercontent.com/113869496/203765374-5b193f5c-4016-4758-8306-7d0b76ea9b6c.png)
</details>
