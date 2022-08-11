# Tempate - Spring 5 (Intellij)
seongsik-kim

## Setting Encoding UTF-8  
* File - Setting
    * Editor - File Encodings
        * Global Encoding, Project Encoding → UTF-8
        * Properties Files - Default encoding ... → UTF-8
        * Transparent native-to-ascii conversion → Check 

* Help - Edit Custom VM Options
    * -Dfile.encoding=UTF-8 추가

## Create Spring5 Project 
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
    * 이동 : servlet-context.xml → WEB-INF/spring/appServlet/servlet-context.xml
    * /pom.xml 파일 수정
        * 초기설정 백업 : pom.xml.init
        
* web.xml 설정 변경
```xml
    <!-- root-context -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/root-context.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- servlet-context    -->
    <servlet>
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

* servlet-context.xml 설정 변경
```xml
    <!-- Enables the Spring MVC @Controller programming model -->
    <mvc:annotation-driven />

    <mvc:default-servlet-handler/>

    <context:component-scan base-package="com.ssk.dev" />

    <!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
    <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <beans:property name="prefix" value="/WEB-INF/views/" />
        <beans:property name="suffix" value=".jsp" />
    </beans:bean>
```

## Tomcat Settings
* Add Configuration... 
    * '+' - Tomcat Server - Local 
    * Name 입력
    * Application Server - Configure... 
        * Tomcat Home 설정 - OK
    * VM options 인코딩 설정
        * -Dfile.encoding=UTF-8
    * (Warning: No artifacts... 발생) Fix
        * [PROJECT_NAME]:war exploded 선택
        * Deployment - context root 경로 설정

* Run Tomcat Server
    * Error: port out of range 발생 시
        * Tomcat Home/conf/server.xml
        * <Server port="8005" 등으로 변경
    
## Log4j2 설정
* /main/resources/log4j2.xml
