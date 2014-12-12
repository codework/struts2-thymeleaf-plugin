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

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.thymeleaf.context.WebContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Extends the {@link org.thymeleaf.context.WebContext} to provide access to the
 * Struts action.
 * <p/>
 * For actions that implement the {@link com.opensymphony.xwork2.LocaleProvider}
 * interface (i.e., actions that extend ActionSupport), the action's locale will
 * be used in this context. Otherwise, the context will default to the
 * {@link javax.servlet.http.HttpServletRequest request's} locale.
 *
 * @author Steven Benitez
 * @since 2.3.15
 */
@Slf4j
public class StrutsContext extends WebContext {
  /**
   * Name of the variable that contains the action.
   */
  public static final String ACTION_VARIABLE_NAME = "action";

  private final Object action;

  public StrutsContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, Object action) {
    super(request, response, servletContext);
    this.action = action;

    if (action instanceof LocaleProvider) {
      setLocale(((LocaleProvider) action).getLocale());
    }
    ValueStack stack = null;
    // If found "TypeConversion Error" , repopulation request parameter.
    if (action instanceof ActionSupport) {
      repopulateConvertionErrorField();
      stack = ActionContext.getContext().getValueStack();
    }



    setVariable(ACTION_VARIABLE_NAME, action);
  }

  public Object getAction() {
    return action;
  }

  /**
   * If found type conversion error ,
   * original request paremeter repopulate same name.
   */
  void repopulateConvertionErrorField() {
	  OgnlValueStack stack = (OgnlValueStack) ActionContext.getContext().getValueStack();
	Map<Object, Object> overrides = stack.getExprOverrides();
	// if ( overrides != null && !overrides.isEmpty()) {
	if (overrides != null) {
		log.debug("overrides:" + overrides.toString());

		Iterator keys = overrides.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			Object overval = overrides.get(key);

			stack.setValue(key, overval, false);
		}

		ActionContext.getContext().setValueStack(stack);
	}
  }
}
