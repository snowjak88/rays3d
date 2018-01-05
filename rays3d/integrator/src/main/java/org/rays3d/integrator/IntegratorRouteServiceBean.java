package org.rays3d.integrator;

import org.rays3d.integrator.holder.IntegratorCachingHolder;
import org.rays3d.integrator.integrators.AbstractIntegrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("integratorRouteService")
public class IntegratorRouteServiceBean {

	@Autowired
	private IntegratorCachingHolder integratorCachingHolder;
}
