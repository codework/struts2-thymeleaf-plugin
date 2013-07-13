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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.thymeleaf.Arguments;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.messageresolver.MessageResolution;

/**
 * A custom {@link org.thymeleaf.messageresolver.IMessageResolver} that uses the
 * Struts2 i18n system to resolve messages.
 *
 * @author Steven Benitez
 * @since 2.3.15
 */
public class StrutsMessageResolver extends AbstractMessageResolver {
  @Override
  public MessageResolution resolveMessage(Arguments arguments, String key, Object[] messageParameters) {
    String message = LocalizedTextUtil.findDefaultText(key, ActionContext.getContext().getLocale(), messageParameters);
    return new MessageResolution(message);
  }
}
