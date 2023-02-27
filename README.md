# 프로젝트 공감
- 많은 사람들에게 공감 서비스를 통해 심리적인 안정감과 휴식을 제공하고 싶어 해당 프로젝트를 시작하게 되었습니다.  

### 프로젝트 구조
<img src=https://user-images.githubusercontent.com/82653075/221523404-e694f1f5-5035-47f7-93e9-7a060234481b.png width="50%"/>

1. 로컬 컴퓨터에서 GitHub로 코드 변경 사항을 푸시합니다.
2. GitHub는 Jenkins에 webhook 알림을 보내 새 빌드를 트리거합니다.
3. Jenkins는 GitHub에서 최신 코드 변경 사항을 가져오고 코드를 빌드하고 테스트합니다.
4. 빌드 및 테스트가 통과되면 Jenkins는 Dockerfile을 사용하여 Docker 이미지를 빌드하고 이미지를 DockerHub에 푸시합니다.
5. Jenkins는 deploy.sh 파일을 프로덕션 EC2 인스턴스로 보내고 스크립트를 실행하여 DockerHub에서 Docker 이미지를 다운로드하고 실행합니다.
6. deploy.sh 스크립트는 Docker를 사용하여 Spring Boot 프로젝트를 시작합니다.
