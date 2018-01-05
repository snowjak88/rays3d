package org.rays3d.builder.java;

import org.rays3d.builder.java.bxdf.LambertianBRDFBuilder;
import org.rays3d.builder.java.bxdf.PerfectSpecularBRDFBuilder;
import org.rays3d.builder.java.camera.PinholeCameraBuilder;
import org.rays3d.builder.java.geometry.Normal3DBuilder;
import org.rays3d.builder.java.geometry.Point2DBuilder;
import org.rays3d.builder.java.geometry.Point3DBuilder;
import org.rays3d.builder.java.geometry.RayBuilder;
import org.rays3d.builder.java.geometry.Vector3DBuilder;
import org.rays3d.builder.java.shape.SphereBuilder;
import org.rays3d.builder.java.spectrum.RGBBuilder;
import org.rays3d.builder.java.spectrum.RGBSpectrumBuilder;
import org.rays3d.builder.java.texture.CheckerboardTextureBuilder;
import org.rays3d.builder.java.texture.ConstantTextureBuilder;
import org.rays3d.builder.java.texture.ImageFileTextureBuilder;
import org.rays3d.builder.java.texture.mapping.LinearTextureMappingBuilder;
import org.rays3d.builder.java.transform.RotationTransformBuilder;
import org.rays3d.builder.java.transform.ScaleTransformBuilder;
import org.rays3d.builder.java.transform.TranslationTransformBuilder;
import org.rays3d.bxdf.BSDF;
import org.rays3d.camera.Camera;
import org.rays3d.shape.Shape;
import org.rays3d.texture.Texture;
import org.rays3d.texture.mapping.TextureMapping;
import org.rays3d.transform.Transform;
import org.rays3d.world.World;

/**
 * Helper class containing a variety of static methods designed to assist with
 * terse {@link World} creation.
 * 
 * @author snowjak88
 */
public class WorldBuilderSupport {

	public static WorldBuilder<? extends AbstractBuilder<?, ?>> world() {

		return new WorldBuilder<>();
	}

	/**
	 * Helper methods for {@link BSDF} creation.
	 */
	public static class BSDFs {

		public static LambertianBRDFBuilder<? extends AbstractBuilder<?, ?>> lambertian() {

			return new LambertianBRDFBuilder<>();
		}

		public static PerfectSpecularBRDFBuilder<? extends AbstractBuilder<?, ?>> perfectSpecular() {

			return new PerfectSpecularBRDFBuilder<>();
		}
	}

	/**
	 * Helper methods for {@link Camera} creation;
	 */
	public static class Cameras {

		public static PinholeCameraBuilder<? extends AbstractBuilder<?, ?>> pinhole() {

			return new PinholeCameraBuilder<>();
		}
	}

	/**
	 * Helper methods for geometrical-primitive creation.
	 */
	public static class Geometry {

		public static Normal3DBuilder<? extends AbstractBuilder<?, ?>> normal() {

			return new Normal3DBuilder<>();
		}

		public static Point2DBuilder<? extends AbstractBuilder<?, ?>> point2D() {

			return new Point2DBuilder<>();
		}

		public static Point3DBuilder<? extends AbstractBuilder<?, ?>> point3D() {

			return new Point3DBuilder<>();
		}

		public static Vector3DBuilder<? extends AbstractBuilder<?, ?>> vector() {

			return new Vector3DBuilder<>();
		}

		public static RayBuilder<? extends AbstractBuilder<?, ?>> ray() {

			return new RayBuilder<>();
		}
	}

	/**
	 * Helper methods for {@link Shape} creation.
	 */
	public static class Shapes {

		public static SphereBuilder<? extends AbstractBuilder<?, ?>> sphere() {

			return new SphereBuilder<>();
		}
	}

	/**
	 * Helper methods for {@link org.rays3d.spectrum.Spectrum}-related creation.
	 */
	public static class Spectrum {

		public static RGBBuilder<? extends AbstractBuilder<?, ?>> rgb() {

			return new RGBBuilder<>();
		}

		public static RGBSpectrumBuilder<? extends AbstractBuilder<?, ?>> spectrum() {

			return new RGBSpectrumBuilder<>();
		}
	}

	/**
	 * Helper methods for {@link Texture} creation.
	 */
	public static class Textures {

		public static CheckerboardTextureBuilder<? extends AbstractBuilder<?, ?>> checkerboard() {

			return new CheckerboardTextureBuilder<>();
		}

		public static ConstantTextureBuilder<? extends AbstractBuilder<?, ?>> constant() {

			return new ConstantTextureBuilder<>();
		}

		public static ImageFileTextureBuilder<? extends AbstractBuilder<?, ?>> image() {

			return new ImageFileTextureBuilder<>();
		}

		/**
		 * Helper methods for {@link TextureMapping} creation.
		 */
		public static class Mappings {

			public static LinearTextureMappingBuilder<? extends AbstractBuilder<?, ?>> linear() {

				return new LinearTextureMappingBuilder<>();
			}

		}
	}

	/**
	 * Helper methods for {@link Transform} creation.
	 */
	public static class Transforms {

		public static RotationTransformBuilder<? extends AbstractBuilder<?, ?>> rotation() {

			return new RotationTransformBuilder<>();
		}

		public static ScaleTransformBuilder<? extends AbstractBuilder<?, ?>> scaling() {

			return new ScaleTransformBuilder<>();
		}

		public static TranslationTransformBuilder<? extends AbstractBuilder<?, ?>> translation() {

			return new TranslationTransformBuilder<>();
		}
	}

}
