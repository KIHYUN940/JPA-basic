package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;

    @Column(name = "zipcode2")
    private String zipcode;


    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }


    /*
    객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
    값 타입은 불변 객체(immutable object)로 설계해야함
    생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
     */

    public String getCity() {
        return city;
    }

//    public void setCity(String city) {
//        this.city = city;
//    }

    public String getStreet() {
        return street;
    }

//    public void setStreet(String street) {
//        this.street = street;
//    }

    public String getZipcode() {
        return zipcode;
    }

//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }
}
