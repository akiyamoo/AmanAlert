package kg.iaau.amanalert.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerFormModel {
    @Schema(description = "ID анкеты")
    Long formId;

    @Schema(description = "Ответ на анкету")
    String answer;
}
