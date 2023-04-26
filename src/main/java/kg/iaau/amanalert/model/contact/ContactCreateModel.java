package kg.iaau.amanalert.model.contact;

import kg.iaau.amanalert.base.BaseModelFrom;
import kg.iaau.amanalert.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactCreateModel implements BaseModelFrom<Contact> {
    String name;
    String phoneNumber;

    @Override
    public Contact ToEntity() {
        return Contact.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}
