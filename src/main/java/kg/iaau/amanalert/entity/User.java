package kg.iaau.amanalert.entity;

import jakarta.persistence.*;
import kg.iaau.amanalert.base.BaseEntity;
import kg.iaau.amanalert.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(unique = true)
    String username;

    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    String phone;

    Date birthDate;

    String name;

    @Builder.Default
    Boolean isActive = true;

    String activateCode;
}