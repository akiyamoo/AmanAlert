package kg.iaau.amanalert.service;

import kg.iaau.amanalert.model.contact.ContactCreateModel;
import kg.iaau.amanalert.model.contact.ContactEditModel;
import kg.iaau.amanalert.model.contact.ContactModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {
    ContactModel addContact(ContactCreateModel createModel);

    ContactModel editContact(ContactEditModel editModel);

    String deleteContact(Long contactId);

    List<ContactModel> getAll();
}
