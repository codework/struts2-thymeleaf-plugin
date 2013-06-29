package org.codework.struts.plugins.thymeleaf;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.thymeleaf.Arguments;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.messageresolver.MessageResolution;

/**
 * A custom {@link org.thymeleaf.messageresolver.IMessageResolver} that uses the Struts2 i18n system to
 * resolve messages.
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
