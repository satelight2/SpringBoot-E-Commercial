package com.devteria.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users", schema = "ecommerce_web")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
     long userId;
    @Basic
    @Column(name = "username")
     String username;
    @Basic
    @Column(name = "email")
     String email;
    @Basic
    @Column(name = "fullname")
     String fullname;
    @Basic
    @Column(name = "status")
     boolean status;
    @Basic
    @Column(name = "password")
     String password;
    @Basic
    @Column(name = "avatar")
     String avatar;
    @Basic
    @Column(name = "phone")
     String phone;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    Date createdAt;
    @Basic
    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
     Date updatedAt;

    @ManyToMany
    Set<RolesEntity> roles;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return userId == that.userId && status == that.status && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(fullname, that.fullname) && Objects.equals(password, that.password) && Objects.equals(avatar, that.avatar) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, fullname, status, password, avatar, phone, address, createdAt, updatedAt);
    }
}
