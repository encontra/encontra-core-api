package pt.inevo.encontra.reflection;

import java.lang.reflect.*;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

public class Instantiator<T> {
    /** Constructor objects of type T needed for instantiating objects */
    private final Constructor<? extends T> constructor;

    public static <T> Instantiator<T> fromTemplate( Object object, int index ) {
        return new Instantiator<T>((Class<T>)getTemplateClass(object,index));
    }
    /**
     * Creates a new instance of ConstructorInstantiator for creating instances of
     * {@code objectClass} via the specified constructor.
     *
     * @param constructor the constructor using which the instances will be created
     * @throws IllegalArgumentException if the provided class does not have a proper constructor
     */
    public Instantiator(Constructor<? extends T> constructor) throws IllegalArgumentException {
        if (Modifier.isAbstract(constructor.getDeclaringClass().getModifiers()))
            throw new IllegalArgumentException("Cannot create abstract " + constructor.getDeclaringClass());
        this.constructor = constructor;
    }

    /**
     * Creates a new instance of ConstructorInstantiator for creating instances of
     * {@code objectClass} that accepts parameters of the given prototype.
     *
     * @param objectClass the class the instances of which will be created
     * @param prototype the types of constructor arguments
     * @throws IllegalArgumentException if the provided class does not have a proper constructor
     */
    public Instantiator(Class<? extends T> objectClass, Class<?>... prototype) throws IllegalArgumentException {
        this(getConstructor(objectClass, prototype));
    }

    /**
     * Retrieves a public constructor with the given prototype from the given class.
     * @param <T> the class in which to search for the constructor
     * @param constructorClass the class in which to search for the constructor
     * @param prototype the constructor prototype
     * @return the constructor found
     * @throws IllegalArgumentException if the there is no constructor for the given prototype
     */
    private static <T> Constructor<T> getConstructor(Class<T> constructorClass, Class<?>... prototype) throws IllegalArgumentException {
        try {
            return constructorClass.getConstructor(prototype);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("There is no constructor " + e.getMessage(), e);
        }
    }

    /**
     * Creates a new instance using the encapsulated constructor.
     * The arguments must be compatible with the prototype that was given while
     * {@link #Instantiator(java.lang.Class, java.lang.Class[]) creating} this
     * {@link Instantiator} class.
     * @param arguments the arguments for the encapsulated constructor
     * @return the new instance
     * @throws IllegalArgumentException if the arguments are not compatible with the constructor prototype
     * @throws InvocationTargetException if there was an exception thrown when the constructor was invoked
     */
    public T instantiate(Object... arguments) throws IllegalArgumentException, InvocationTargetException {
        try {
            return constructor.newInstance(arguments);
        } catch (InstantiationException e) {
            throw new InternalError("Cannot call " + constructor + ": " + e); // This should never happen - the class is not abstract
        } catch (IllegalAccessException e) {
            throw new InternalError("Cannot call " + constructor + ": " + e); // This should never happen - the constructor is public
        }
    }

    /**
     * @param object instance of a class that is a subclass of a generic class
     * @param index index of the generic type that should be instantiated
     * @return new instance of T (created by calling the default constructor)
     * @throws RuntimeException if T has no accessible default constructor
     */
    public static <T> Class<T> getTemplateClass( Object object, int index ) {
        ParameterizedType superClass =
            (ParameterizedType )object.getClass().getGenericSuperclass();
        Type type = superClass.getActualTypeArguments()[ index ];
        Class<T> instanceType;
        if( type instanceof ParameterizedType ) {
            instanceType = (Class<T> )( (ParameterizedType )type ).getRawType();
        }
        else {
            instanceType = (Class<T> )type;
        }
        return instanceType;
    }
}
