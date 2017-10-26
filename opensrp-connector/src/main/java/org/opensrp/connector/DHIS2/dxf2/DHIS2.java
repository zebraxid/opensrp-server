package org.opensrp.connector.DHIS2.dxf2;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DHIS2 {
    String dataSetId() default "";
    String dateElementId() default "";
    String categoryOptionId() default "";
}
