--
--
-- Insert a test World.
insert into world  ( id, version, created, name, description, file ) values ( 1, 1, CURRENT_DATE, 'Test World', 'This is a test World for development purposes', '"world:{ }' );
--
--
-- Insert a test Render.
insert into render_descriptor ( version, created, world_id, film_width, film_height, sampler_name, samples_per_pixel, integrator_name, extra_integrator_config, rendering_status, sampling_status, integration_status, film_status, image_mime_type, image_data ) values ( 1, CURRENT_DATE, 1, 320, 240, 'stratified-sampler', 4, 'path-tracing-integrator', null, 'NOT_STARTED', 'NOT_STARTED', 'NOT_STARTED', 'NOT_STARTED', null, null );
--