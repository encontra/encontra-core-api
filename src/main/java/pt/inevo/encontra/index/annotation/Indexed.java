package pt.inevo.encontra.index.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Mark this property as indexed.  Use the "name" attribute to override the
 * field name, the "nested" attribute to specify whether it should be
 * indexed when the object is a property on another Searchable, the
 * "stored" attribute to specify whether it should be stored (defaults to
 * false), the "boost" attribute to set a boost value, the "tokenized"
 * attribute to change whether it is tokenized (defaults to true), and the
 * storeTermVector" attribute to specify whether a term vector should be
 * stored.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Indexed {
    /**
     * @return Boost factor.
     */
    float boost() default 1F;
}
