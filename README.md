# Tempate - Spring 5 - JPA
seongsik-kim

1. [Spec](#Spec)
1. [Dependencies](#Dependencies)
1. [H2 Database](#H2-Database)
1. [JPA](#JPA)
1. [Spring Data JPA](#Spring-Data-JPA)
1. [JPA Join](#JPA-JOIN)

## Spec
* Spring 5.3.22
* JDK 1.8.0_231
* H2 Database
* Intellij 및 STS 호환

## Dependencies
* pom.xml
```xml

        <!--template-spring-jpa-->
        <!--H2 Database-->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.github.javafaker/javafaker -->
        <dependency>
            <groupId>com.github.javafaker</groupId>
            <artifactId>javafaker</artifactId>
            <version>1.0.2</version>
        </dependency>
        <!--Lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.24</version>
            <scope>provided</scope>
        </dependency>

        <!-- Swagger 2 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>

        <!--JPA-->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.6.9.Final</version>
        </dependency>
```

## H2 Database
* root-context.xml
```xml
    <!-- DB접속 DataSource 빈생성 -->
    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="org.h2.Driver" />
        <property name="url" value="jdbc:h2:~/test" />
        <property name="username" value="sa" />
        <property name="password" value="" />
    </bean>
```


## Swagger
* SwaggerConfig.java 구현
* (Optional) ApiInfo Method 구현하여 Document 설명.
 
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }
}
```


## JPA
### Spring JPA 설정
* root-context.xml 에 정의함. 
```xml
       <!-- JPA 설정 -->
       <bean id="entityManagerFactory"
             class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
           <property name="dataSource" ref="dataSource"/>
           <property name="jpaVendorAdapter">
               <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                   <property name="generateDdl" value="true" />
               </bean>
           </property>
           <property name="packagesToScan" value="com.ssk.dev"/>
   
           <property name="jpaProperties">
               <props>
                   <prop key="hibernate.dialect">org.hibernate.dialect.H2Dialect</prop>

                   <prop key="hibernate.max_fetch_depth">3</prop>
                   <prop key="hibernate.jdbc.fetch_size">50</prop>
                   <prop key="hibernate.jdbc.batch_size">10</prop>
                   <prop key="hibernate.show_sql">true</prop>
                   <prop key="hibernate.format_sql=true">true</prop>

                <!-- Entity 의 field name 을 Camelcase 로 사용할 수 있도록 -->
                <prop key="hibernate.physical_naming_strategy">com.ssk.dev.config.HibernatePhysicalNamingStrategy</prop>
               </props>
           </property>
       </bean>
   
       <bean id="transactionManager"
             class="org.springframework.orm.jpa.JpaTransactionManager">
           <property name="entityManagerFactory" ref="entityManagerFactory" />
       </bean>
```
* transactionManager 
    * EntityManagerFactory는 트랜잭션 기반 데이터 엑세스 시 사용할 트랜잭션 매니저를 필요로 한다.
    * JPA는 전용 TransactionManager를 제공.
    
* JPA EntityManagerFactory 
    * 하이버네이트 사용을 위해 jpaVendorAdapter 프로퍼티에 HibernateJpaVendorAdapter 클래스 설정.  
    * packagesToScan 을 이용해 도메인 객체를 스캔할 수 있도록 패키지 지정. (Spring 3.1 이후 persistance.xml 을 대체함.)

* Naming Strategy
    * @Column(name = "member_id") 등을 사용하지 않아도 Camel case 를 Snake case 로 치환해 준다. 
    * PhysicalNamingStrategyStandardImpl 를 상속받아 Overriding.
    * (Spring boot 의 경우 SpringPhysicalNamingStrategy 를 기본 제공한다)
    
```java
public class HibernatePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name);
    }

    private Identifier convertToSnakeCase(Identifier identifier) {
        if (identifier == null) {
            return null;
        }
        String name = identifier.getText();
        String newName = name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}

```

### Spring Data JPA
* JPA EntityManager를 래핑해 더 단순화된 JPA기반 데이터엑세스 인터페이스를 제공. 
* Repository의 추상화를 위해 JpaConfig.java 에 추가로 정의.

```java
@Configuration
@EnableJpaRepositories("com.ssk.dev.repository")
@EnableTransactionManagement
public class JpaConfig {

}
```

#### JpaRepository
* 배치, 페이징, 정렬 기능을 제공하는 진보된 인터페이스. 
* 어플리케이션 복잡도에 따라 CrudRepository, JpaRepository 중 선택하여 사용. 
* JpaRepository는 CrudRepository를 상속하여 모든 기능을 지원. 


#### Domain 정의
* @Entity annotation 정의
* @Id 를 이용해 PK 정의  
```java
@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberId;
    private String name;
}
```
    
#### Repository 정의
* JpaRepository 를 상속, spring-data-jpa 가 제공하는 jpa 인터페이스 함수 사용가능
```java
public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

#### DTO 정의 
* API 에서 Domain Class 를 그대로 반환하는 경우, DB Table 변동 등의 상황에서 API Spec 변동 발생.
* DTO 로 Wrapping 하여 API Sepc 변동을 방지 
```java
@Data
public class MemberDto {
    private Long memberId;
    private String name;

    public MemberDto(Member o) {
        this.memberId = o.getMemberId();
        this.name = o.getName();
    }
}
```    
    
#### Response 정의 
* 목록 반환 API에서 배열을 반환하는 경우, 운영 중 API 항목 추가하기 매우 어려움. 
* 반환 데이터의 확장성과 표준화를 고려하여 Response Data로 Wrapping. 
```java
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int count;
    private T data;
}
``` 

#### Paging
* servlet-context 또는 Configuration 에 페이징 관련 Argument Resolover 정의. 
```xml
    <mvc:annotation-driven>
        <mvc:argument-resolvers>
            <ref bean="sortResolver"/>
            <ref bean="pageableResolver" />
        </mvc:argument-resolvers>
    </mvc:annotation-driven>

    <bean id="sortResolver" class="org.springframework.data.web.SortHandlerMethodArgumentResolver" />
    <bean id="pageableResolver" class="org.springframework.data.web.PageableHandlerMethodArgumentResolver">
        <constructor-arg ref="sortResolver" />
    </bean>
```
```java
@Configuration
@EnableSpringDataWebSupport
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add( new PageableHandlerMethodArgumentResolver());
    }
}
```

* Controller Parameter 로 Pageable 정의
* repository Parameter 로 Pageable 전달
```java
public ApiResponse<List<MemberDto>> members(Pageable pageable) {
    List<Member> members = memberRepository.findAll(pageable).getContent();
}
``` 


### JPA JOIN
#### Eager Loading
* ManyToOne, OneToOne 관계에서 주로 사용.
* Orders.java
```java
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
```

#### Lazy Loading
* OneToMany, ManyToMany 관계에서 주로 사용.
* Lazy Loading 은 최초 Proxy Object 에 바인딩 처리하며, 객체 접근 시 데이터를 조회한다.  
* Orders.java
```java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
```

#### Nullable Join
* Outer Join 수행
    * @ManyToOne 관계에서 optional 속성이 true(default)인 경우 
    * @JoinColumn 의 nullable 속성이 true(default) 인 경우
    
* Inner Join 수행 (필수 관계)
    * @ManyToOne 관계에서 optional 속성이 false 인 경우 
    * @JoinColumn 의 nullable 속성이 false 인 경우


#### N+1 Problem
* 목록 쿼리 실행 시, 레코드 수만큼 반복해 쿼리가 실행되어 성능이 저하되는 현상. 
* FetchType 과 무관하게 발생할 수 있다. 

##### Fetch Join
* JPQL 로 직접 조인문 작성. 
* OneToOne, ManyToOne 관계에서 사용.
* OneToMany 관계에서는 Cartesian Product 에 의해 페이징 처리 불가능함.

##### Entity Graph
* @EntityGraph 에 명시. Outer Join 처리됨. 
* OneToOne, ManyToOne 관계에서 사용.
* OneToMany 관계에서는 Cartesian Product 에 의해 페이징 처리 불가능함.

##### FetchMode
* @Fetch 명시.
* OneToMany 관계에서 사용. 
* IN 절을 이용해 대상 레코드를 찾아 매핑.  
```java
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItem;
```
* SQL 2회 수행. 또는 (Count / BatchSize)+1 회 수행됨. 
* hibernate.default_batch_fetch_size 기본 BatchSize를 정의할 수 있다. 
     
