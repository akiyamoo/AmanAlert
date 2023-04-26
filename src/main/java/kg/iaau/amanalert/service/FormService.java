package kg.iaau.amanalert.service;

import kg.iaau.amanalert.model.form.AnswerFormModel;
import kg.iaau.amanalert.model.form.FormCreateModel;
import kg.iaau.amanalert.model.form.FormModel;

import java.util.List;

public interface FormService {
    List<FormModel> getActiveForms();
    String deleteFormById(Long id);
    FormModel createForm(FormCreateModel createModel);
    FormModel saveAnswer(AnswerFormModel answerFormModel);
}
