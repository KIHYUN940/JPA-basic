package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") //연관관계의 주인이 아니면 mappedBy 속성으로 지정 / mappedBy가 적힌 곳은 읽기(조회)만 가능
    private List<Member> members = new ArrayList<>(); // 리스트를 초기화 하는 이유는 NULL 포인터 예방 - 초기화 하지 않은 상태에서 members.add(new Member()); -> 널 포인터 발생 -> 관례로 리스트는 할상 초기화 시켜준다.


    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }

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
}
