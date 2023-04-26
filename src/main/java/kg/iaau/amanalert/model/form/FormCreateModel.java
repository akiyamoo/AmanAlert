package kg.iaau.amanalert.model.form;

import kg.iaau.amanalert.base.BaseModelFrom;
import kg.iaau.amanalert.entity.Form;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormCreateModel implements BaseModelFrom<Form> {
    String name;
    String eventDescription;
    String eventLocation;
    String eventTime;

    @Override
    public Form ToEntity() {
        return Form.builder()
                .name(name)
                .eventDescription(eventDescription)
                .eventLocation(eventLocation)
                .eventTime(eventTime)
                .build();
    }
}
