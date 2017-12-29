package org.rays3d.texture;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.rays3d.geometry.Point2D;
import org.rays3d.interact.SurfaceDescriptor;
import org.rays3d.shape.Shape;
import org.rays3d.spectrum.RGB;
import org.rays3d.texture.mapping.LinearTextureMapping;

public class ImageFileTextureTest {

	private ImageFileTexture texture;

	@Before
	public void setUp() throws Exception {

		this.texture = new ImageFileTexture(new File("build/resources/test/2x2_test.png"), new LinearTextureMapping());
	}

	@Test
	public void testEvaluate_red() {

		final Point2D point = new Point2D(0.1, 0.1);
		final RGB rgb = texture.evaluate(new SurfaceDescriptor<Shape>((Shape) null, null, null, point)).toRGB();
		assertEquals("Evaluated-Red not as expected!", 1.0, rgb.getRed(), 1d / 256d);
		assertEquals("Evaluated-Green not as expected!", 0.0, rgb.getGreen(), 1d / 256d);
		assertEquals("Evaluated-Blue not as expected!", 0.0, rgb.getBlue(), 1d / 256d);
	}

	@Test
	public void testEvaluate_green() {

		final Point2D point = new Point2D(0.9, 0.1);
		final RGB rgb = texture.evaluate(new SurfaceDescriptor<Shape>((Shape) null, null, null, point)).toRGB();
		assertEquals("Evaluated-Red not as expected!", 0.0, rgb.getRed(), 1d / 256d);
		assertEquals("Evaluated-Green not as expected!", 1.0, rgb.getGreen(), 1d / 256d);
		assertEquals("Evaluated-Blue not as expected!", 0.0, rgb.getBlue(), 1d / 256d);
	}

	@Test
	public void testEvaluate_blue() {

		final Point2D point = new Point2D(0.1, 0.9);
		final RGB rgb = texture.evaluate(new SurfaceDescriptor<Shape>((Shape) null, null, null, point)).toRGB();
		assertEquals("Evaluated-Red not as expected!", 0.0, rgb.getRed(), 1d / 256d);
		assertEquals("Evaluated-Green not as expected!", 0.0, rgb.getGreen(), 1d / 256d);
		assertEquals("Evaluated-Blue not as expected!", 1.0, rgb.getBlue(), 1d / 256d);
	}

	@Test
	public void testEvaluate_white() {

		final Point2D point = new Point2D(0.9, 0.9);
		final RGB rgb = texture.evaluate(new SurfaceDescriptor<Shape>((Shape) null, null, null, point)).toRGB();
		assertEquals("Evaluated-Red not as expected!", 1.0, rgb.getRed(), 1d / 256d);
		assertEquals("Evaluated-Green not as expected!", 1.0, rgb.getGreen(), 1d / 256d);
		assertEquals("Evaluated-Blue not as expected!", 1.0, rgb.getBlue(), 1d / 256d);
	}

}
