package kg.iaau.amanalert.entity;

import jakarta.persistence.*;
import kg.iaau.amanalert.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "FORM")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Form extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    String name;

    @Column(length = 2048)
    String eventDescription;

    @Column(length = 2048)
    String eventLocation;

    @Column(length = 2048)
    String eventTime;

    @Column(length = 2048)
    String answer;

    Date deleted;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    User user;
}