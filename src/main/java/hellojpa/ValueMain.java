package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

//        Integer a = new Integer(10);
//        Integer b = a;  // 래퍼 클래스 - 이때는 primitive 타입처럼 값이 넘어가는 것이 아니라 레퍼런스(주소)가 넘어간다.

//        a.setValue(20); // 예를 들어 a값을 바꾸게 되면 a의 레퍼런스(주소)가 b에 넘어가기에 둘다 같은 인스턴스를 공유하게 된다. -> 공유는 가능한 객체지만 변경은 X -> 변경이 되는게 아니라 Side Effect가 발생하지 않음
        
//        int a = 10;
//        int b = a;
//        a = 20;

//        System.out.println("a = " + a);
//        System.out.println("b = " + b);

        /*
        자바의 기본 타입 primitive type 같은 경우는 절대 공유 X
        -> Side Effect가 발생하지 않는다.
         */


        /*
        값 타입은 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야함
        객체 타입은 주소 값을 참조하기 때문에 값이 같아도 값으로 비교하면 false가 나옴

        동일성 비교 : 인스턴스의 참조 값을 비교, == 사용
        동등성 비교 : 인스턴스의 값을 비교, equals() 사용
        -> 그렇기 때문에 값 타입은 a.equals(b)를 사용하여 동등성 비교를 해야한다.
         */

        int a = 10;
        int b = 10;
        System.out.println("a == b : " + (a == b)); //true

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");
        System.out.println("address1 == address2 : " + (address1 == address2)); //false
        System.out.println("address1 equals address2 : " + (address1.equals(address2))); //equals 오버라이드 설정을 해놓을 시 true

        // 값 타입의 비교는 항상 equals를 사용하자 / 현업에선 equals로 비교를 할 일이 생각보다 없긴 하다.
    }
}
