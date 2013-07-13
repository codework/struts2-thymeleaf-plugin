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

/**
 * A default implementation of {@link TemplateEngineProvider}.
 *
 * @author Steven Benitez
 * @since 2.3.15
 */
public class DefaultTemplateEngineProvider implements TemplateEngineProvider {
  private TemplateEngine templateEngine;

  public DefaultTemplateEngineProvider() {
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    // HTML5 is the future!
    templateResolver.setTemplateMode("HTML5");

    // This will convert "home" to "/WEB-INF/templates/home.html"
    //templateResolver.setPrefix("/WEB-INF/templates/");
    //templateResolver.setSuffix(".html");

    templateResolver.setCacheable(true);
    // Set template cache TTL to 1 hour. If not set, entries would live in cache
    // until expelled by LRU
    templateResolver.setCacheTTLMs(3600000L);

    templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    templateEngine.setMessageResolver(new StrutsMessageResolver());
  }

  @Override
  public TemplateEngine get() {
    return templateEngine;
  }
}
