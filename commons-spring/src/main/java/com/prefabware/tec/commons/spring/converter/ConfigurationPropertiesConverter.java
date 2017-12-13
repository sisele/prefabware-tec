package com.prefabware.tec.commons.spring.converter;

import java.lang.annotation.*;

/**
 * Created by stefan on 17.11.17.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigurationPropertiesConverter {
}
