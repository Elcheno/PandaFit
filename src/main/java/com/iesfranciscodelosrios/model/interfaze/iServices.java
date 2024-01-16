package com.iesfranciscodelosrios.model.interfaze;

import java.util.UUID;

public interface iServices <T> {
    public T findById(UUID id);

    public T save(T t);

    public T delete(T t);

}
