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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.codework.struts.plugins.thymeleaf.spi.TemplateEngineProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.context.SpringWebContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;

/**
 * Renders a Thymeleaf-Spring template as the result of invoking a Struts action.
 *
 * @author Steven Benitez ( Update A-pZ )
 * @since 2.3.15
 * @version 2.3.20 ( Update A-pZ )
 */
//@Slf4j
public class ThymeleafSpringResult implements Result {
	private String defaultEncoding = "UTF-8";
	private TemplateEngineProvider templateEngineProvider;
	private String templateName;

	/**
	 * The result parameter name to set the name of the template to.
	 * <p/>
	 * IMPORTANT! Struts2 will look for this field reflectively to determine
	 * which parameter is the default. This allows us to have a simplified
	 * result configuration. Don't remove it!
	 */
	public static final String DEFAULT_PARAM = "templateName";

	/** instance name of struts2-action */
	public static final String ACTION_VARIABLE_NAME = "action";

	/** field errors */
	public static final String FIELD_ERRORS_NAME ="field";

	/** struts2 convertion errors fields and value */
	public static final String OVERRIDES_NAME = "overrides";

	public ThymeleafSpringResult() {
	}

	public ThymeleafSpringResult(String templateName) {
		this.templateName = templateName;
	}

	@Override
	public void execute(ActionInvocation actionInvocation) throws Exception {
		TemplateEngine templateEngine = templateEngineProvider.get();

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletContext servletContext = ServletActionContext
				.getServletContext();

		Object action = actionInvocation.getAction();

		// Action instance put to Thymeleaf context.
		Map<String, Object> variables = bindStrutsContest(action);

		// Locale by Struts2-Action.
		Locale locale = ((LocaleProvider) action).getLocale();

		// Spring-ApplicationContext.
		//ApplicationContext appctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ApplicationContext appctx =
				(ApplicationContext)servletContext.getAttribute( WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		// Use SpringWebContext( by Thymeleaf-spring plugin. )
		SpringWebContext context = new SpringWebContext(request, response,
				servletContext, locale, variables, appctx);

		// response to TemplateEngine.
		response.setContentType("text/html");
		response.setCharacterEncoding(defaultEncoding);
		templateEngine.process(templateName, context, response.getWriter());
	}

	@Inject(StrutsConstants.STRUTS_I18N_ENCODING)
	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	@Inject
	public void setTemplateEngineProvider(
			TemplateEngineProvider templateEngineProvider) {
		this.templateEngineProvider = templateEngineProvider;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * Binding Struts2 action and context, field-errors , convertion error values bind another name.
	 * @param action Action instance
	 * @return ContextMap
	 */
	Map<String, Object> bindStrutsContest(Object action) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ACTION_VARIABLE_NAME, action);

		if ( action instanceof ActionSupport) {
			ActionSupport actSupport = (ActionSupport)action;

			// Struts2 field errors.( Map<fieldname , fielderrors>)
			Map<String, List<String>> fieldErrors = actSupport.getFieldErrors();
			variables.put(FIELD_ERRORS_NAME, fieldErrors);
		}

		return variables;
	}
}
