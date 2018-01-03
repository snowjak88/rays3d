package org.rays3d.builder.bxdf;

import org.rays3d.builder.AbstractBuilder;
import org.rays3d.bxdf.BSDF;

public interface BSDFBuilder<T extends BSDF, P extends AbstractBuilder<?, ?>> extends AbstractBuilder<T, P> {

}
