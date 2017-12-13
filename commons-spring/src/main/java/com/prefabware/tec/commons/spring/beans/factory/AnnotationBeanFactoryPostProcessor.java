package com.prefabware.tec.commons.spring.beans.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.prefabware.tec.commons.exception.LocalExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

/**
 * Created by stefan on 17.11.17.
 */
public abstract class AnnotationBeanFactoryPostProcessor<C> implements BeanFactoryPostProcessor{

	@Autowired
	BeanDefinitionRegistry registry;

	final Class<C>candidateClass;
	Class<Annotation> annotationType;

	public AnnotationBeanFactoryPostProcessor(Class<C> candidateClass) {
		this.candidateClass = candidateClass;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Arrays.stream(beanFactory.getBeanNamesForType(candidateClass))
				.filter(beanName-> isBeanMethodAnnotated(beanName))
				.forEach(beanName -> {
					registerBeanDefinitionFor(beanFactory, beanName);
				});
	}

	protected abstract void registerBeanDefinitionFor(ConfigurableListableBeanFactory beanFactory, String beanName);


	boolean isBeanMethodAnnotated(String beanName){
		Method method = factoryMethod(beanName);
		Annotation annotation = AnnotationUtils.findAnnotation(method, annotationType);
		return annotation!=null;
	}
	/**
	 * to find the @Bean annotated method,that created the bean with the given name
	 * @param beanName
	 * @return
	 */
	Method factoryMethod(String beanName){
		BeanDefinition definition = registry.getBeanDefinition(beanName);
		String factoryBeanName = definition.getFactoryBeanName();
		BeanDefinition configurationDefinition = registry.getBeanDefinition(factoryBeanName);
		String configurationClassName = configurationDefinition.getBeanClassName();
		Class<?> configurationClass=null;
		try {
			configurationClass = Class.forName(configurationClassName);
		} catch (ClassNotFoundException e) {
			LocalExceptionHandler.rethrow(e);
		}
		String beanMethod = definition.getFactoryMethodName();
		return ReflectionUtils.findMethod(configurationClass,beanMethod);
	};
}
