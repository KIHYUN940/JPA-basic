package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

        Integer a = new Integer(10);
        Integer b = a;  // 래퍼 클래스 - 이때는 primitive 타입처럼 값이 넘어가는 것이 아니라 레퍼런스(주소)가 넘어간다.

//        a.setValue(20); // 예를 들어 a값을 바꾸게 되면 a의 레퍼런스(주소)가 b에 넘어가기에 둘다 같은 인스턴스를 공유하게 된다. -> 공유는 가능한 객체지만 변경은 X -> 변경이 되는게 아니라 Side Effect가 발생하지 않음
        
//        int a = 10;
//        int b = a;
//        a = 20;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        /*
        자바의 기본 타입 primitive type 같은 경우는 절대 공유 X
        -> Side Effect가 발생하지 않는다.
         */
    }
}
