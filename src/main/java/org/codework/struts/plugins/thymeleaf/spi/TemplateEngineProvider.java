package org.codework.struts.plugins.thymeleaf.spi;

import org.thymeleaf.TemplateEngine;

/**
 * Provides Struts with a Thymeleaf {@link org.thymeleaf.TemplateEngine} to use when rendering
 * templates.
 *
 * @author Steven Benitez
 * @see DefaultTemplateEngineProvider
 * @since 2.3.15
 */
public interface TemplateEngineProvider {
  /**
   * Returns the TemplateEngine to use to render a Thymeleaf template. This
   * method will be called repeatedly, so implementations should create the
   * actual TemplateEngine object outside of this method. For an example, refer
   * to {@link DefaultTemplateEngineProvider}.
   *
   * @return The TemplateEngine to use to render a Thymeleaf template.
   */
  TemplateEngine get();
}
