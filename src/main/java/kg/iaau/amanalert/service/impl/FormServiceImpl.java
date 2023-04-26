package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.entity.Form;
import kg.iaau.amanalert.entity.User;
import kg.iaau.amanalert.model.form.AnswerFormModel;
import kg.iaau.amanalert.model.form.FormCreateModel;
import kg.iaau.amanalert.model.form.FormModel;
import kg.iaau.amanalert.repo.FormRepository;
import kg.iaau.amanalert.service.FormService;
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
public class FormServiceImpl implements FormService {

    FormRepository repository;
    UserEndPoint userEndPoint;

    @Override
    public List<FormModel> getActiveForms() {
        return repository.findAllByDeletedIsNull()
                .stream()
                .map(f -> new FormModel().toModel(f))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteFormById(Long id) {
        Form form = repository.findById(id).orElseThrow(() -> new NotFoundException("Form not found"));
        form.setDeleted(new Date());
        repository.save(form);
        return "Form deleted";
    }

    @Override
    public FormModel createForm(FormCreateModel createModel) {
        User user = userEndPoint.getCurrentUser();
        Form form = createModel.ToEntity();
        form.setUser(user);

        return new FormModel().toModel(repository.save(form));
    }

    @Override
    public FormModel saveAnswer(AnswerFormModel answerFormModel) {
        Form form = repository.findById(answerFormModel.getFormId()).orElseThrow(() -> new NotFoundException("Form not found"));
        form.setAnswer(answerFormModel.getAnswer());

        return new FormModel().toModel(repository.save(form));
    }
}
