package ironfist.container;

public interface Predicate<T> {

  boolean invoke(T object);

}
