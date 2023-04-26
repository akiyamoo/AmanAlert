package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.entity.Contact;
import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.model.contact.ContactCreateModel;
import kg.iaau.amanalert.model.contact.ContactEditModel;
import kg.iaau.amanalert.model.contact.ContactModel;
import kg.iaau.amanalert.repo.ContactRepository;
import kg.iaau.amanalert.service.ContactService;
import kg.iaau.amanalert.service.endPoint.UserEndPoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContactServiceImpl implements ContactService {
    ContactRepository repository;
    UserEndPoint userEndPoint;

    @Override
    public ContactModel addContact(ContactCreateModel createModel) {
        User user = userEndPoint.getCurrentUser();
        Contact contact = createModel.ToEntity();
        contact.setUser(user);
        contact = repository.save(contact);

        return new ContactModel().toModel(contact);
    }

    @Override
    public ContactModel editContact(ContactEditModel editModel) {
        Contact contact = repository.findByIdAndDeletedIsNull(editModel.getId()).orElseThrow(
                () -> new NotFoundException("Contact not found")
        );
        contact.setName(editModel.getName());
        contact.setPhoneNumber(editModel.getPhoneNumber());

        return new ContactModel().toModel(repository.save(contact));
    }

    @Override
    public String deleteContact(Long contactId) {
        Contact contact = repository.findByIdAndDeletedIsNull(contactId).orElseThrow(
                () -> new NotFoundException("Contact not found")
        );
        contact.setDeleted(new Date());
        repository.save(contact);

        return "Contact deleted";
    }

    @Override
    public List<ContactModel> getAll() {
        User user = userEndPoint.getCurrentUser();

        return repository.findAllByUserAndDeletedIsNull(user)
                .stream()
                .map(c -> new ContactModel().toModel(c))
                .collect(Collectors.toList());
    }
}