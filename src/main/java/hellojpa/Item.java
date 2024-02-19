package hellojpa;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  // 일반적으로 조인 전략이 정석이라고 보고 사용하면 좋다. / 구현 클래스마다 테이블 전략은 사용하면 안되는 전략 // JPA의 큰 장점 중 하나 - 큰 코드를 바꾸지 않고 InheritanceType만 바꿔줬는데도 데이터베이스 전략을 바꿀 수 있다.
@DiscriminatorColumn // 조인 전략에선 이 애너테이션이 없으면 안 생겼는데  싱글 테이블 전략에선 이 애너테이션이 없어도 DTYPE이 생긴다. / 결론은 DTYPE이 있는게 운영상 좋다. // TABLE_PER_CLASS 전략에선 이 애너테이션을 넣어도 DTYPE을 사용하지 않기에 의미가 없음
public abstract class Item { //구현 클래스마다 테이블 전략 - TABLE_PER_CLASS을 사용하면 Item(슈퍼 클래스) 테이블을 생성하지 않고 각각의 구현 클래스(서브 클래스)에 해당하는 테이블이 생성

    /*
    기본적으로 조인 전략을 가져가는데, 데이터가 되게 단순하다거나 확장 가능성이 낮을 때는 단일 테이블 전략을 사용
    - 조인 전략은 중요한 정보나 비즈니스 코드가 복잡할 때 주로 사용
    - DBA와 논의 후 전략 사용 지향
     */

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
