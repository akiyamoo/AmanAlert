package kg.iaau.amanalert.entity;

import jakarta.persistence.*;
import kg.iaau.amanalert.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "USER")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    String username;

    String password;

    @Enumerated(EnumType.STRING)
    Role role;
}