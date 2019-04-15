package ohm.softa.a04;

import java.util.Iterator;
import java.util.function.Function;

public interface SimpleList<T> extends Iterable<T> {
	/**
	 * Add a given object to the back of the list.
	 */
	void add(T item);

	/**
	 * @return current size of the list
	 */
	int size();

	default void addDefault(Class<T> clazz){
		try {
			this.add(clazz.newInstance());
		}
		catch(IllegalAccessException|InstantiationException e){
			e.printStackTrace();
		}

	};
	/**
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 */
	default SimpleList filter(SimpleFilter<T> filter){
		SimpleList<T> result;
		try {
			result = (SimpleList<T>)getClass().newInstance();
		}
		catch(InstantiationException|IllegalAccessException e){
			result=new SimpleListImpl<>();
		}
		for(T item : this){
			if(filter.include(item)){
				result.add(item);
			}
		}
		return result;
	}

	default <R> SimpleList<R> map(Function<T, R> transform){
		SimpleList<R> result;
		try {
			result = (SimpleList<R>)getClass().newInstance();
		}
		catch(InstantiationException|IllegalAccessException e){
			result=new SimpleListImpl<>();
		}
		for(T item : this){
			R transformedItem = transform.apply(item);
			result.add(transformedItem);
		}
		return result;
	}
}
