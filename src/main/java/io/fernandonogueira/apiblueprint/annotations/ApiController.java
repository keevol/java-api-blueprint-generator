package io.fernandonogueira.apiblueprint.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Fernando Nogueira
 * @since 3/8/15 1:26 AM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ApiController {

    /**
     * Controller endpoint (e.g. /YourEntity)
     */
    String endPoint();

    /**
     * The description of the controller
     */
    String description();

    /**
     * The resource class (POJO) that this controller is responsible.
     * <p/>
     * You may have many controllers for the same resource/POJO.
     */
    String resouceClass();

}