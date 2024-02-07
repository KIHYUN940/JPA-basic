package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//@Table(name = "USER") // 테이블 네임
public class Member {

    @Id
    private Long id;

//    @Column(name = "username") // 컬럼 네임
    private String name;

    public Member(){} // JPA는 리플렉션을 사용하여 엔티티 객체를 생성하는데 이때 기본 생성자가 없으면 객체를 생성할 수 없다.

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
