package kg.iaau.amanalert.controller;

import kg.iaau.amanalert.enums.Role;
import kg.iaau.amanalert.model.form.AnswerFormModel;
import kg.iaau.amanalert.model.form.FormCreateModel;
import kg.iaau.amanalert.service.FormService;
import kg.iaau.amanalert.service.GrantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/form")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FormController {

    FormService formService;
    GrantService grantService;

    @GetMapping("/get-all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<?>> getAllNews() {
        return ResponseEntity.ok(formService.getActiveForms());
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> create(@RequestBody FormCreateModel createModel) {
        grantService.hasAny(Role.MOBILE_USER, Role.ADMIN);

        try {
            return ResponseEntity.ok(formService.createForm(createModel));
        } catch (Exception e) {
            log.error("saveNews(): {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/save-answer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> saveAnswer(@RequestBody AnswerFormModel answerFormModel) {
        grantService.hasAny(Role.WEB_USER, Role.ADMIN);

        try {
            return ResponseEntity.ok(formService.saveAnswer(answerFormModel));
        } catch (Exception e) {
            log.error("saveNews(): {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{formId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteForm(@PathVariable Long formId) {
        grantService.hasAny(Role.WEB_USER, Role.ADMIN);

        try {
            return ResponseEntity.ok(formService.deleteFormById(formId));
        } catch (Exception e) {
            log.error("saveNews(): {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
