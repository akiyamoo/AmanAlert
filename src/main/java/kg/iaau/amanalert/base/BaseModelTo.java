package kg.iaau.amanalert.base;

import lombok.Data;

public interface BaseModelTo<TypeEntity> {
    <T extends BaseModelTo<TypeEntity>> T toModel(TypeEntity entity);
}
