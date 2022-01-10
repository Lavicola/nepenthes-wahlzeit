package org.wahlzeit.model;

public @interface PatternInstance {
    String patternName() default "";
    String[] participants() default "";

}
