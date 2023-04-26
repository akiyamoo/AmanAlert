package kg.iaau.amanalert.controller;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.model.contact.ContactCreateModel;
import kg.iaau.amanalert.model.contact.ContactEditModel;
import kg.iaau.amanalert.service.ContactService;
import kg.iaau.amanalert.service.GrantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContactController {

    GrantService grantService;
    ContactService contactService;

    @GetMapping("/get-all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getContactsByCurrentUser() {
        return ResponseEntity.ok(contactService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addContact(@RequestBody ContactCreateModel createModel) {
        grantService.hasAny(Role.MOBILE_USER);

        try {
            return ResponseEntity.ok(contactService.addContact(createModel));
        } catch (Exception e) {
            log.error("saveNews(): {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addContact(@RequestBody ContactEditModel editModel) {
        grantService.hasAny(Role.MOBILE_USER);

        try {
            return ResponseEntity.ok(contactService.editContact(editModel));
        } catch (Exception e) {
            log.error("saveNews(): {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{contactId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addContact(@PathVariable Long contactId) {
        grantService.hasAny(Role.MOBILE_USER);

        try {
            return ResponseEntity.ok(contactService.deleteContact(contactId));
        } catch (Exception e) {
            log.error("saveNews(): {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
