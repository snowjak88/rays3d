package org.rays3d.integrator.integrators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.rays3d.world.World;
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
public class NamedIntegratorScanner implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger									LOG						= LoggerFactory
			.getLogger(NamedIntegratorScanner.class);

	@Autowired
	private ApplicationContext									context;

	private final Map<String, Constructor<AbstractIntegrator>>	integratorConstructors	= new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		LOG.info("Scanning classpath for named [" + AbstractIntegrator.class.getName() + "] implementations.");

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false,
				context.getEnvironment());

		scanner.resetFilters(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Named.class));
		scanner.addIncludeFilter(new AssignableTypeFilter(AbstractIntegrator.class));
		Set<BeanDefinition> foundIntegratorDefinitions = scanner
				.findCandidateComponents(this.getClass().getPackage().getName());

		for (BeanDefinition integratorDefinition : foundIntegratorDefinitions) {
			try {
				Class<?> integratorClass = Class.forName(integratorDefinition.getBeanClassName());

				final String integratorName = integratorClass.getAnnotation(Named.class).value();
				final Constructor<AbstractIntegrator> integratorConstructor = (Constructor<AbstractIntegrator>) integratorClass
						.getConstructor(World.class, String.class);

				LOG.info("Detected integrator: \"" + integratorName + "\" [" + integratorClass.getName() + "]");
				integratorConstructors.put(integratorName, integratorConstructor);

			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				LOG.error("Unexpected exception while scanning for Samplers!", e);
			}
		}
	}

	/**
	 * For a given name, attempt to construct a new integrator instance. If no
	 * integrator has that name, throws a {@link NoSuchElementException}.
	 * 
	 * @param name
	 * @param world
	 * @param extraConfiguration
	 * @return
	 */
	public AbstractIntegrator getIntegratorByRenderId(String name, World world, String extraConfiguration) {

		if (!integratorConstructors.containsKey(name))
			throw new NoSuchElementException("No such integrator detected with name \"" + name + "\".");

		try {
			return integratorConstructors.get(name).newInstance(world, extraConfiguration);

		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			LOG.error("Unexpected exception while initializing new \"" + name + "\" named-integrator instance.", e);
		}

		return null;
	}

}
