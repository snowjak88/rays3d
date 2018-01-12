package org.rays3d.sampler.samplers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.rays3d.message.sample.SampleRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

@Component
public class NamedSamplerScanner implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger						LOG					= LoggerFactory
			.getLogger(NamedSamplerScanner.class);

	@Autowired
	private ApplicationContext						context;

	private final Map<String, Constructor<Sampler>>	samplerConstructors	= new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		LOG.info("Scanning classpath for named [" + Sampler.class.getName() + "] implementations.");

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false,
				context.getEnvironment());

		scanner.resetFilters(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Named.class));
		scanner.addIncludeFilter(new AssignableTypeFilter(Sampler.class));
		Set<BeanDefinition> foundSamplerDefinitions = scanner
				.findCandidateComponents(this.getClass().getPackage().getName());

		for (BeanDefinition samplerDefinition : foundSamplerDefinitions) {
			try {
				Class<?> samplerClass = Class.forName(samplerDefinition.getBeanClassName());

				final String samplerName = samplerClass.getAnnotation(Named.class).value();
				final Constructor<Sampler> samplerConstructor = (Constructor<Sampler>) samplerClass
						.getConstructor(SampleRequestMessage.class);

				LOG.info("Detected Sampler: \"" + samplerName + "\" [" + samplerClass.getName() + "]");
				samplerConstructors.put(samplerName, samplerConstructor);

			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				LOG.error("Unexpected exception while scanning for Samplers!", e);
			}
		}
	}

	/**
	 * For a given name, attempt to construct a new {@link Sampler} instance. If
	 * no Sampler has that name, throws a {@link NoSuchElementException}.
	 * 
	 * @param name
	 * @param sampleRequest
	 * @return
	 */
	public Sampler getSamplerByName(String name, SampleRequestMessage samplerRequest) {

		if (!samplerConstructors.containsKey(name))
			throw new NoSuchElementException("No such Sampler detected with name \"" + name + "\".");

		try {
			return samplerConstructors.get(name).newInstance(samplerRequest);

		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			LOG.error("Unexpected exception while initializing new \"" + name + "\" named-Sampler instance.", e);
		}

		return null;
	}

}
