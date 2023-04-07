package kg.iaau.amanalert.base;

import lombok.Data;

public abstract class BaseModelTo<TypeEntity> {
    public abstract <T extends BaseModelTo<TypeEntity>> T toModel(TypeEntity entity);
}
