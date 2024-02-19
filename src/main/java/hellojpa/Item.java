package hellojpa;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // JPA의 큰 장점 중 하나 - 큰 코드를 바꾸지 않고 InheritanceType만 바꿔줬는데도 데이터베이스 전략을 바꿀 수 있다.
@DiscriminatorColumn // 조인 전략에선 이 애너테이션이 없으면 안 생겼는데  싱글 테이블 전략에선 이 애너테이션이 없어도 DTYPE이 생긴다. / 결론은 DTYPE이 있는게 운영상 좋다.
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
