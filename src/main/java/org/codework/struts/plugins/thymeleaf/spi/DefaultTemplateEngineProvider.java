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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.codework.struts.plugins.thymeleaf.StrutsMessageResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

/**
 * A default implementation of {@link TemplateEngineProvider}.
 *
 * @author Steven Benitez
 * @since 2.3.15
 */
@Slf4j
public class DefaultTemplateEngineProvider implements TemplateEngineProvider {
	// HTML5 is the future!
	private String templateMode = "HTML5";
	private String characterEncoding = "UTF-8";
	// This will convert "home" to "/WEB-INF/templates/home.html"
	private String prefix = "/WEB-INF/templates/";
	private String suffix = ".html";
	private boolean cacheable = true;

	// Default template cache TTL to 1 hour. If not set, entries would live in
	// cache until expelled by LRU.
	private Long cacheTtlMillis = 3600000L;

	protected TemplateEngine templateEngine;

	private String templateEngineName;
	private Container container;
	private Map<String, TemplateEngine> templateEngines = new HashMap<String, TemplateEngine>();

	/**
	 * Configure settings from the struts.xml or struts.properties, using
	 * sensible defaults if values are not provided.
	 */
	public void configure() {
		log.debug(" configure.");

		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setTemplateMode(templateMode);
		templateResolver.setCharacterEncoding(characterEncoding);
		templateResolver.setPrefix(prefix);
		templateResolver.setSuffix(suffix);
		templateResolver.setCacheable(cacheable);
		templateResolver.setCacheTTLMs(cacheTtlMillis);

		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.setMessageResolver(new StrutsMessageResolver());
	}

	@Override
	public TemplateEngine get() {
		return templateEngine;
	}

	@Inject(value = "struts.thymeleaf.templateMode", required = false)
	public void setTemplateMode(String templateMode) {
		this.templateMode = templateMode;
	}

	@Inject(value = "struts.thymeleaf.encoding", required = false)
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
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

	@Inject(value = "struts.thymeleaf.cacheTtlMillis", required = false)
	public void setCacheTtlMillis(String cacheTtlMillis) {
		this.cacheTtlMillis = Long.parseLong(cacheTtlMillis);
	}

	/**
	 * loading di container configulation from struts-plugins.xml , choise thymeleaf template engine.
	 * @param container
	 */
	@Inject
	public void setContainer(Container container) {
		log.debug("loading di container config.");
		this.container = container;

		Map<String, TemplateEngine> map = new HashMap<String, TemplateEngine>();

		Set<String> prefixes = container.getInstanceNames(TemplateEngine.class);
		for (String prefix : prefixes) {
			TemplateEngine engine = (TemplateEngine) container.getInstance(TemplateEngine.class, prefix);

			map.put(prefix, engine);
			log.debug(" -- prefix:" + prefix + " / engine:" + engine.getClass().getName());
		}
		this.templateEngines = Collections.unmodifiableMap(map);
	}

	/**
	 * Thymeleaf template type loading from struts.properties.
	 * @param templateEngineType ( default | spring )
	 */
	@Inject(value = "struts.thymeleaf.templateEngineName", required = true)
	public void setTemplateEngineName(String templateEngineName) {
		this.templateEngine = templateEngines.get(templateEngineName);

		log.debug(" - use template engine name :" + templateEngineName);
		log.debug("       template engine class:" + this.templateEngine);
		// configure template engine.
		configure();
	}


}
