package com.faultaddr.coffeebeggerserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "CoffeeBegger", catalog = "")
public class MUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "avatar", nullable = false, length = 255)
    private String avatar;
    @Basic
    @Column(name = "city", nullable = true, length = 255)
    private String city;
    @Basic
    @Column(name = "gender", nullable = true, length = 100)
    private String gender;
    @Basic
    @Column(name = "province", nullable = true, length = 100)
    private String province;
    @Basic
    @Column(name = "country_code", nullable = true, length = 100)
    private String countryCode;
    @Basic
    @Column(name = "nick_name", nullable = true, length = 100)
    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
