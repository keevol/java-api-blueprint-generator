package io.fernandonogueira.apiblueprint.annotations;

import io.fernandonogueira.apiblueprint.enums.ApiRequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This must be used to map the API Request methods that you want to add to your API Documentation
 *
 * @author Fernando Nogueira
 * @since 3/8/15 1:25 AM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ApiRequest {

    /**
     * The description of this request
     */
    String description();

    /**
     * Endpoint suffix. This will be appended this way: {controllerEndpoint/apiRequestEndpoint}
     */
    String endPoint();

    /**
     * Response code for this request (if this annotations is used on a method)
     * Default is 200
     */
    String responseCode() default "200";

    /**
     * Response type if this is a method request
     * Default is application/json
     */
    String responseContentType() default "application/json";

    /**
     * The request method (PUT, GET, POST, DELETE, etc)
     */
    ApiRequestMethod method();

}