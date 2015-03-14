package japiblueprint.annotations;

/**
 * @author Fernando Nogueira
 * @since 3/8/15 12:18 PM
 */
public @interface ApiResourceAttr {

    String description();
    boolean mandatory() default false;

}