package kg.iaau.amanalert.model.contact;

import kg.iaau.amanalert.base.BaseModelTo;
import kg.iaau.amanalert.entity.Contact;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactModel implements BaseModelTo<Contact> {

    Long id;
    String name;
    String phoneNumber;
    @Override
    public ContactModel toModel(Contact contact) {
        return ContactModel.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phoneNumber(contact.getPhoneNumber())
                .build();
    }
}
