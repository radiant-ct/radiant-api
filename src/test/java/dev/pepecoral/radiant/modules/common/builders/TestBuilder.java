package dev.pepecoral.radiant.modules.common.builders;

public interface TestBuilder<T> {
    T entity();

    T persist(TestPersistenceContext ctx);
}
