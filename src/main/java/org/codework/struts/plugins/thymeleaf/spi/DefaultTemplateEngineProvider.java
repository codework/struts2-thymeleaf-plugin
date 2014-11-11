/*
 * Copyright 2013 Steven Benitez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codework.struts.plugins.thymeleaf.spi;

import org.codework.struts.plugins.thymeleaf.StrutsMessageResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.opensymphony.xwork2.inject.Inject;

/**
 * A default implementation of {@link TemplateEngineProvider}.
 *
 * @author Steven Benitez
 * @since 2.3.15
 */
public class DefaultTemplateEngineProvider implements TemplateEngineProvider {
	private TemplateEngine templateEngine;

	public DefaultTemplateEngineProvider() {

	}

	/**
	 * Configure setting from struts.properties|struts.xml.
	 * @see Inject
	 */
	public void configure() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		// HTML5 is the future!
		templateResolver.setTemplateMode(this.templateMode);
    templateResolver.setCharacterEncoding("UTF-8");

		// This will convert "home" to "/WEB-INF/templates/home.html"
		templateResolver.setPrefix(this.prefix);
		templateResolver.setSuffix(this.suffix);

		templateResolver.setCacheable(this.cacheable);
		// Set template cache TTL to 1 hour. If not set, entries would live in
		// cache
		// until expelled by LRU
		templateResolver.setCacheTTLMs(this.cacheTTLMs);

		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.setMessageResolver(new StrutsMessageResolver());
	}

	@Override
	public TemplateEngine get() {
		if (templateEngine == null) {
			configure();
		}

		return templateEngine;
	}

	@Inject(value = "struts.thymeleaf.templateMode", required = false)
	public void setTemplateMode(String templateMode) {
		this.templateMode = templateMode;
	}

	@Inject(value = "struts.thymeleaf.prefix", required = false)
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Inject(value = "struts.thymeleaf.suffix", required = false)
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Inject(value = "struts.thymeleaf.cacheable", required = false)
	public void setCacheable(String cacheable) {
		this.cacheable = Boolean.parseBoolean(cacheable);
	}

	@Inject(value = "struts.thymeleaf.cacheTTLMs", required = false)
	public void setCacheTTLMs(String cacheTTLMs) {
		this.cacheTTLMs = Long.parseLong(cacheTTLMs);
	}

	private String templateMode = "HTML5";
	private String prefix = "/WEB-INF/templates/";
	private String suffix = ".html";
	private boolean cacheable = true;
	private Long cacheTTLMs = 3600000L;
}
