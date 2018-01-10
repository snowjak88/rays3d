--
--
-- Insert a test World.
insert into world_descriptor  ( id, version, created, name, description, text ) values ( 1, 1, CURRENT_DATE, 'Test World', 'This is a test World for development purposes', 'world { camera Cam.pinhole { imagePlaneSize 4, 3; eye { x 1; y 0.5; z(-8) }; lookAt { x 0; y 0; z 0 }; up Vector3D.J; focalLength 8 }; primitive { shape Shape.sphere { radius 1.5; transform Tfm.translate { dx (-3); dy 0; dz 0 } }; bsdf BSDF.perfectSpecular { tint Txtr.constant { spectrum Spec.rgbSpectrum { rgb RGB.GREEN } } } }; primitive { shape Shape.sphere { radius 1.5; transform Tfm.translate { dx (+3); dy 0; dz 0 } }; bsdf BSDF.lambertian { texture Txtr.constant { spectrum Spec.rgbSpectrum { rgb RGB.RED } } } }; primitive { shape Shape.sphere { radius 0.5; transform Tfm.translate { dx 0; dy (+5); dz 0 } }; bsdf BSDF.lambertian { texture Txtr.constant { spectrum Spec.rgbSpectrum { rgb RGB.WHITE } }; emissive Spec.rgbSpectrum { r 10; g 10; b 10 } } } }' );
--
--
-- Insert a test Render.
insert into render_descriptor ( version, created, world_descriptor_id, film_width, film_height, sampler_name, samples_per_pixel, integrator_name, extra_integrator_config, rendering_status ) values ( 1, CURRENT_DATE, 1, 320, 240, 'pseudorandom-sampler', 4, 'path-tracing-integrator', null, 'NOT_STARTED' );
--