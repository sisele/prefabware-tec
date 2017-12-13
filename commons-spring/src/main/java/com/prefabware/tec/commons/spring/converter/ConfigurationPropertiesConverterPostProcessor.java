package com.prefabware.tec.commons.spring.converter;

import com.prefabware.tec.commons.spring.beans.factory.AnnotationBeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by stefan on 17.11.17.
 */
public class ConfigurationPropertiesConverterPostProcessor extends AnnotationBeanFactoryPostProcessor {
	public ConfigurationPropertiesConverterPostProcessor() {
		super(Converter.class);
	}

	/**
	 * to add an adapter for every converter annotated
	 * @param beanFactory
	 * @param beanName
	 */
	@Override
	protected void registerBeanDefinitionFor(ConfigurableListableBeanFactory beanFactory, String beanName) {

	}
}
