package com.example.takvimnothaziran;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String tcNo;
    private String phone;
    private String email;
    private String address;

    public User() {
        // Boş yapıcı metod gereklidir (Firebase ile çalışırken).
    }

    public User(String userId, String firstName, String lastName, String username, String password, String tcNo, String phone, String email, String address) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.tcNo = tcNo;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    public User(String firstName, String lastName, String username, String password, String tcNo, String phone, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.tcNo = tcNo;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
