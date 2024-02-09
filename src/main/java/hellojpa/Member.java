package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity // JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
public class Member {

    @Id
    private Long id;

    @Column(name = "name", nullable = false) //컬럼 매핑 / 객체에는 username을 쓰고 싶은데 DB컬럼에는 name을 써야할 때
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) // ENUM 타입 매핑 / ORDINAL 사용 X STRING 타입 권장 - 이름을 데이터베이스에 저장
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //날짜 타입 매핑 / 날짜와 시간 둘다 주기 위해 TIMESTAMP 사용
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // 최신 버전엔 LocalDate 사용
    private LocalDate testLocalDate; // 년, 월만 포함
    private LocalDateTime testLocalDateTime; // 년, 월, 일 포함

    @Lob // BLOB, CLOB 매핑 / 데이터베이스에 큰 컨텐츠를 넣고 싶으면 @Lob 사용  / 매핑하는 필드 타입이 문자 타입이면 clob으로 매핑, 나머지는 blob 매핑
    private String description;

    @Transient // 특정 필드를 컬럼에 매핑하지 않음(매핑 무시) / 주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용
    private int temp;

    public Member(){} // JPA는 리플렉션을 사용하여 엔티티 객체를 생성하는데 이때 기본 생성자가 없으면 객체를 생성할 수 없다. / 기본 생성자 필수


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
