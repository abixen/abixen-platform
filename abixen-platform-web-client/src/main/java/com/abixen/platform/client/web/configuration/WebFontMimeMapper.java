package com.abixen.platform.client.web.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFontMimeMapper implements EmbeddedServletContainerCustomizer {

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		mappings.add("woff", "application/font-woff");
		mappings.add("woff2", "application/font-woff2");
		mappings.add("ttf", "application/x-font-truetype");
		mappings.add("eot", "application/vnd.ms-fontobject");
		mappings.add("svg", "image/svg+xml");
		mappings.add("otf", "application/x-font-opentype");
		container.setMimeMappings(mappings);
	}
}
