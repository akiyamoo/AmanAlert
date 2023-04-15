package kg.iaau.amanalert.entity;

import jakarta.persistence.*;
import kg.iaau.amanalert.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "NEWS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class News extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column
    String title;

    @Column(length = 2048)
    String description;

    @Column
    @Builder.Default
    byte[] image = new byte[0];

    @Column
    String imageName;
}
