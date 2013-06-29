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
    // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
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
