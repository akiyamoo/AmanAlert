package kg.iaau.amanalert.model.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactEditModel {
    Long id;
    String name;
    String phoneNumber;
}
