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
package org.codework.struts.plugins.thymeleaf;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.codework.struts.plugins.thymeleaf.spi.TemplateEngineProvider;
import org.thymeleaf.TemplateEngine;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;

/**
 * Renders a Thymeleaf template as the result of invoking a Struts action.
 *
 * @author Steven Benitez
 * @since 2.3.15
 */
public class ThymeleafResult implements Result {
  private String defaultEncoding = "UTF-8";
  private TemplateEngineProvider templateEngineProvider;
  private String templateName;

  /**
   * The result parameter name to set the name of the template to.
   * <p/>
   * IMPORTANT! Struts2 will look for this field reflectively to determine which
   * parameter is the default. This allows us to have a simplified result
   * configuration. Don't remove it!
   */
  public static final String DEFAULT_PARAM = "templateName";

  public ThymeleafResult() {
  }

  public ThymeleafResult(String templateName) {
    this.templateName = templateName;
  }

  @Override
  public void execute(ActionInvocation actionInvocation) throws Exception {
    TemplateEngine templateEngine = templateEngineProvider.get();

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    ServletContext servletContext = ServletActionContext.getServletContext();

    Object action = actionInvocation.getAction();
    StrutsContext context = new StrutsContext(request, response, servletContext, action);
    response.setContentType("text/html");
    response.setCharacterEncoding(defaultEncoding);
    templateEngine.process(templateName, context, response.getWriter());
  }

  @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
  public void setDefaultEncoding(String defaultEncoding) {
    this.defaultEncoding = defaultEncoding;
  }

  @Inject
  public void setTemplateEngineProvider(TemplateEngineProvider templateEngineProvider) {
    this.templateEngineProvider = templateEngineProvider;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }
}
