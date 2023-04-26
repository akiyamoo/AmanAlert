package kg.iaau.amanalert.model.form;

import kg.iaau.amanalert.base.BaseModelTo;
import kg.iaau.amanalert.entity.Form;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormModel implements BaseModelTo<Form> {
    Long id;
    String name;
    String eventDescription;
    String eventLocation;
    String eventTime;
    String answer;
    Long userId;

    @Override
    public FormModel toModel(Form form) {
        id = form.getId();
        name = form.getName();
        eventDescription = form.getEventDescription();
        eventLocation = form.getEventLocation();
        eventTime = form.getEventTime();
        answer = form.getAnswer();
        userId = form.getUser() == null ? null : form.getUser().getId();

        return this;
    }
}
