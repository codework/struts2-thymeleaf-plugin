/**
 *
 */
package org.codework.struts.plugins.thymeleaf.diarect;

import java.util.List;
import java.util.Map;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;
import org.thymeleaf.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * IF Struts2-Fielderror raise, append css-class for field.
 *
 * @author A-pZ
*/
public class Struts2FieldErrorAttrProcessor extends
		AbstractStandardSingleAttributeModifierAttrProcessor {

	public static final int ATTR_PRECEDENCE = 1010;
	public static final String ATTR_NAME = "errorclass";
	public static final String TARGET_ATTR_NAME = "class";

	/**
	 * Default Constructor.
	 */
	public Struts2FieldErrorAttrProcessor() {
		super(ATTR_NAME);
	}

	/**
	 * Target attirbute name to append class.
	 *
	 * @see org.thymeleaf.processor.attr.AbstractSingleAttributeModifierAttrProcessor#getTargetAttributeName(org.thymeleaf.Arguments,
	 *      org.thymeleaf.dom.Element, java.lang.String)
	 */
	@Override
	protected String getTargetAttributeName(Arguments arguments,
			Element element, String attributeName) {
		return TARGET_ATTR_NAME;
	}

	/**
	 * Get attribute value 'errorclass'.
	 *
	 * @return errorclass value
	 */
	protected String getTargetAttributeValue(final Arguments arguments,
			final Element element, final String attributeName) {

		Object action = ActionContext.getContext().getActionInvocation()
				.getAction();
		// check action instance 'ActionSupport'.
		if (!(action instanceof ActionSupport)) {
			return BLANK;
		}

		ActionSupport asupport = (ActionSupport) action;
		Map<String, List<String>> fieldErrors = asupport.getFieldErrors();
		if (fieldErrors == null || fieldErrors.size() == 0) {
			return BLANK;
		}

		final String fieldName = element.getAttributeValue("name");
		if (StringUtils.isEmptyOrWhitespace(fieldName)) {
			return BLANK;
		}

		List<String> fieldErrorList = fieldErrors.get(fieldName);
		if ( fieldErrorList == null || fieldErrorList.size() == 0 ) {
			return BLANK;
		}

		return super.getTargetAttributeValue(arguments, element, attributeName);
	}

	/**
	 * Attribute write type. APPEND_WITH_SPACE is append attirbute before white
	 * space.
	 *
	 * @see org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#getModificationType(org.thymeleaf.Arguments,
	 *      org.thymeleaf.dom.Element, java.lang.String, java.lang.String)
	 */
	@Override
	protected ModificationType getModificationType(Arguments arguments,
			Element element, String attributeName, String newAttributeName) {
		return ModificationType.APPEND_WITH_SPACE;
	}

	/**
	 * Empty attribute behavior.
	 *
	 * @see org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#removeAttributeIfEmpty(org.thymeleaf.Arguments,
	 *      org.thymeleaf.dom.Element, java.lang.String, java.lang.String)
	 */
	@Override
	protected boolean removeAttributeIfEmpty(Arguments arguments,
			Element element, String attributeName, String newAttributeName) {
		return true;
	}

	/**
	 * Return this processor precedence.
	 *
	 * @see org.thymeleaf.processor.AbstractProcessor#getPrecedence()
	 */
	@Override
	public int getPrecedence() {
		return ATTR_PRECEDENCE;
	}

	private static final String BLANK = "";

}
