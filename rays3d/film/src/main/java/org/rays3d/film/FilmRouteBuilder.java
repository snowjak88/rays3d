package org.rays3d.film;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.rays3d.film.holder.FilmUpdateBean;
import org.rays3d.message.sample.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilmRouteBuilder extends RouteBuilder {

	@Autowired
	private FilmUpdateBean filmUpdateBean;

	@Override
	public void configure() throws Exception {

		//
		//@formatter:off
		//
		//
		from("activemq:rays3d.film.samples")
			.inputType(Sample.class)
			.log(LoggingLevel.TRACE, "Received new Sample (render-ID ${body.renderId} @ [${body.filmPoint.x},${body.filmPoint.y}])")
			.bean(filmUpdateBean, "addSample");
		//
		//
		//@formatter:on
		//
	}

}
