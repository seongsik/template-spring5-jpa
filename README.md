# Study Spring 5

seongsik-kim

## Create Spring5 Project (Intellij)
* File - New - Project
    * Maven Project 생성
* Project 우클릭 - Add Framework Support
    * Spring - Spring MVC
    * Configure... - Spring Version 선택
    
* /lib 디렉터리 삭제
* File - Project Structure
    * Project Settings - Libraries - 삭제

## Spring Tool Suite(STS) 구조에 맞추기 
#### Web Resource Directory 변경
* /web 디렉터리를 /src/main/ 하위로 이동
* /src/main/web 디렉터리명 변경 : webapp
* File - Project Structure
    * Project Settings - Modules - Web
    * Web Resource Directory 경로 재설정 
    
#### Spring Context Setting
* Create context file directory
    * /src/main/webapp/WEB-INF/spring/appServlet
    * /src/main/webapp/WEB-INF/views
    * /src/test/resources (maven source directories)
    
* Modify context file name
    * applicationContext.xml → root-context.xml
    * dispatcher-servlet.xml → servlet-context.xml
    * /pom.xml 파일 수정
        * 초기설정 백업 : pom.xml.init