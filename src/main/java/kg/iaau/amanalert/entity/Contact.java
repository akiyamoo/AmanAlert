package kg.iaau.amanalert.entity;

import jakarta.persistence.*;
import kg.iaau.amanalert.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "CONTACTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    String name;

    String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    User user;
}