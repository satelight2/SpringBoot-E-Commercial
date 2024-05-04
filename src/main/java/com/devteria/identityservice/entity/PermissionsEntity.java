package com.devteria.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "permissions", schema = "ecommerce_web")
public class PermissionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "permission_id")
     long permissionId;
    @Basic
    @Column(name = "name")
     String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionsEntity that = (PermissionsEntity) o;
        return permissionId == that.permissionId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, name);
    }
}
