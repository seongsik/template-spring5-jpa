# Tempate - Spring 5 - JPA
seongsik-kim

JPA 핵심은 EntityManager Interface. 


1. pom.xml
1. H2 Database 
1. Faker
1. Swagger
1. JPA


## pom.xml
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


## Faker

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
       <!-- DB접속 DataSource 빈생성 -->
       <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
           <property name="driverClassName" value="org.h2.Driver" />
           <property name="url" value="jdbc:h2:~/test;AUTO_SERVER=true" />
           <property name="username" value="sa" />
           <property name="password" value="" />
       </bean>
   
   
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
    * packagesToScan 을 이용해 도메인 객체를 스캔할 수 있도록 패키지 지정. (Sp3.1 이후 persistance.xml 을 대체함.)

* naming strategy
    * @Column(name = "member_id") 등을 사용하지 않아도 Camelcase 를 Snakecase 로 치환해 준다. 
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

### JPA JOIN
#### Eager Loading
* Orders.java
```java
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
```

#### Lazy Loading
* Lazy Loading 은 최초 Proxy Object 에 바인딩 처리하며, 객체 접근 시 데이터를 조회한다.  
* Orders.java
```java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
```

