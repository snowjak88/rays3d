package org.rays3d.javabuilder.bxdf;

import org.rays3d.bxdf.BSDF;
import org.rays3d.javabuilder.AbstractBuilder;

public interface BSDFBuilder<T extends BSDF, P extends AbstractBuilder<?, ?>> extends AbstractBuilder<T, P> {

}
