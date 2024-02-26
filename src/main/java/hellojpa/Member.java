package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity // JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //Period - 기간
    @Embedded
    private Period workPeriod;

    //Address - 주소
    @Embedded
    private Address homeAddress;

    //한 엔티티에서 같은 값 타입 사용 시
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
//            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
//            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))
//    })
//    private Address workAddress;





//    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩 LAZY를 사용하면 team을 프록시 객체로 조회한다.
////    @ManyToOne(fetch = FetchType.EAGER) //즉시 로딩 - 예를 들어 Member와 Team을 조회할 때 항상 묶어서 조회할 때 EAGER 사용
//    @JoinColumn(name = "TEAM_ID") // 일대다에서 양방향 적용 - insert, upate를 false로 적용해서 결과적으로 읽기 전용으로 만듦 - 이런 매핑은 공식적으로 존재 X - 실무에서 복잡하게 하다보면 쓸 일이 가끔 생기기도 함 -> * 결론은 일대다 단방향, 양방향 쓰지말고 다대일 양방향으로 사용!!
//    private Team team;

//    @OneToOne
//    @JoinColumn(name = "LOCKER_ID")
//    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();

//    @OneToMany(mappedBy = "member")
//    private List<MemberProduct> memberProducts = new ArrayList<>();


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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
