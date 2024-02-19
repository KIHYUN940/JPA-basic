package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;


    // 일대다 연관관계 - 1이 연관관계 주인 - 외래키 지정  <-> 테이블은 항상 다 쪽에 외래키가 있음
    @OneToMany
    @JoinColumn(name = "TEAM_ID")  // @JoinColumn을 꼭 사용하자 - 사용하지 않으면 조인테이블 방식을 사용(중간 테이블 하나 추가) -> 성능 저하 및 복잡해짐
    private List<Member> members = new ArrayList<>(); // 리스트를 초기화 하는 이유는 NULL 포인터 예방 - 초기화 하지 않은 상태에서 members.add(new Member()); -> 널 포인터 발생 -> 관례로 리스트는 할상 초기화 시켜준다.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }


    /*
    실무에서는 컨트롤러에서 엔티티를 반환하지 말고 DTO를 활용해서 엔티티를 변환하자
     */

    // 양방향 매핑 시 toString() 사용하면 무한루프 발생 - 사용 지양
//    @Override
//    public String toString() {
//        return "Team{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", members=" + members +
//                '}';
//    }
}
