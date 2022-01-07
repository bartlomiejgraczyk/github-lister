package pl.bgraczyk.githublister.client.abstraction;

@FunctionalInterface
public interface SessionManager<T> {

    T create();
}
