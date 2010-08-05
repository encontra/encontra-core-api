package pt.inevo.encontra.index.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

/**
 * Annotation utility methods.
 *
 * @author Seth Fitzsimmons
 */
public class AnnotationUtils {

    /**
     * Get a specific annotation present on a class.
     *
     * This differs from AnnotatedElement.getAnnotation(Annotation) in that it
     * looks up the class hierarchy for inherited annotations.  (@Inherit only
     * applies to class-level annotations.)  It also checks declarations within
     * interfaces.
     *
     * @see java.lang.reflect.AnnotatedElement#getAnnotations()
     *
     * @param clazz Class to check for present annotations.
     * @param annotation Annotation to look for.
     * @return Instance of the specified annotation or null if not present.
     */
    public static Annotation getAnnotation(Class clazz,
            final Class<? extends Annotation> annotation) {
        return getAnnotation(clazz, annotation, false);
    }

    /**
     * Get a specific annotation present on or in a class.
     *
     * This differs from AnnotatedElement.getAnnotation(Annotation) in that it
     * looks up the class hierarchy for inherited annotations.  (@Inherit only
     * applies to class-level annotations.)  It also checks declarations within
     * interfaces.
     *
     * @see java.lang.reflect.AnnotatedElement#getAnnotations()
     *
     * @param clazz Class to check for present annotations.
     * @param annotationClass Annotation class to look for.
     * @param includeMethods Whether to include methods when searching.
     * @return Instance of the specified annotation or null if not present.
     */
    public static Annotation getAnnotation(Class clazz,
            final Class<? extends Annotation> annotationClass,
            final boolean includeMethods) {

        if (null == clazz || null == annotationClass) {
            return null;
        }

        Annotation annotation = null;

        if (includeMethods) {
            for (final Method m : clazz.getMethods()) {
                if (isAnnotationPresent(m, annotationClass)) {
                    annotation = getAnnotation(m, annotationClass);
                }
            }
        }

        // check all superclasses and inherited interfaces
        for (final Class<? extends Object> c : AnnotationUtils.getClasses(clazz)) {
            if (c.isAnnotationPresent(annotationClass)) {
                annotation = c.getAnnotation(annotationClass);
            }
        }

        return annotation;
    }

    /**
     * Get a specific annotation present on a method.
     *
     * This differs from AnnotatedElement.getAnnotation(Annotation) in that it
     * looks up the class hierarchy for inherited annotations.  (@Inherit only
     * applies to class-level annotations.)  It also checks declarations within
     * interfaces.
     *
     * @see java.lang.reflect.AnnotatedElement#getAnnotations()
     *
     * @param method Method to check for present annotations.
     * @param annotationClass Annotation to look for.
     * @return Instance of the specified annotation or null if not present.
     */
    public static Annotation getAnnotation(final Method method,
            final Class<? extends Annotation> annotationClass) {

        if (null == method || null == annotationClass) {
            return null;
        }

        Annotation annotation = null;

        // check all superclasses and inherited interfaces
        for (final Class c : AnnotationUtils.getClasses(method.getDeclaringClass())) {
            try {
                final Method m = c.getMethod(method.getName(), method.getParameterTypes());
                if (m.isAnnotationPresent(annotationClass)) {
                    annotation = m.getAnnotation(annotationClass);
                }
            } catch (final NoSuchMethodException e) {
            }
        }

        return annotation;
    }

    /**
     * Gets a Collection of classes extended and interfaces implemented by the
     * specified class (including itself).
     *
     * This could be done with ClassUtils, but this is more direct.
     *
     * @param clazz Class to inspect.
     * @return Collection of classes extended and interfaces implemented.
     */
    private static Collection<Class> getClasses(Class clazz) {
        final Collection<Class> classes = new HashSet<Class>();
        while (null != clazz) {
            classes.add(clazz);

            // add implemented interfaces to the list of classes to check
            for (final Class iface : clazz.getInterfaces()) {
                classes.add(iface);
            }

            clazz = clazz.getSuperclass();
        }
        return classes;
    }

    /**
     * Determine whether a class hierarchy is annotated with a specific
     * annotation.
     *
     * This differs from AnnotatedElement.getAnnotations() in that it looks up
     * the class hierarchy for inherited annotations.  (@Inherit only applies
     * to class-level annotations.)  It also checks declarations within
     * interfaces.
     *
     * @see java.lang.reflect.AnnotatedElement#getAnnotations()
     *
     * @param clazz Class to check for present annotations.
     * @param annotation Annotation to look for.
     * @return Whether the specified annotation is present on a given class.
     */
    public static boolean isAnnotationPresent(final Class clazz,
            final Class<? extends Annotation> annotation) {
        return (null != getAnnotation(clazz, annotation));
    }

    /**
     * Determine whether a method (or methods that it overrides) are annotated
     * with a specific annotation.
     *
     * This differs from AnnotatedElement.getAnnotations() in that it looks up
     * the class hierarchy for inherited annotations.  (@Inherit only applies
     * to class-level annotations.)  It also checks declarations within
     * interfaces.
     *
     * @see java.lang.reflect.AnnotatedElement#getAnnotations()
     *
     * @param method Method to check for present annotations.
     * @param annotation Annotation to look for.
     * @return Whether the specified annotation is present on a given method.
     */
    public static boolean isAnnotationPresent(final Method method,
            final Class<? extends Annotation> annotation) {
        return (null != getAnnotation(method, annotation));
    }
}
