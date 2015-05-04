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
 * Field error css class processor for Struts2-Thymeleaf plugin.
 * @author A-pZ
 *
 */
public class Struts2ErrorClassAttrProcessor extends
		AbstractStandardSingleAttributeModifierAttrProcessor {

	public static final int ATTR_PRECEDENCE = 10100;
    public static final String ATTR_NAME = "errorclass";
    public static final String TARGET_ATTR_NAME = "class";

    /**
     * Default Constructor.
     */
	public Struts2ErrorClassAttrProcessor() {
		super(ATTR_NAME);
	}

	/**
	 * Update to attribute name.
	 * @see org.thymeleaf.processor.attr.AbstractSingleAttributeModifierAttrProcessor#getTargetAttributeName(org.thymeleaf.Arguments, org.thymeleaf.dom.Element, java.lang.String)
	 */
	@Override
	protected String getTargetAttributeName(Arguments arguments,
			Element element, String attributeName) {
		return TARGET_ATTR_NAME;
	}

	/**
	 * Attribute update type. APPEND_WITH_SPACE is attribute value append with white-space.
	 * @see org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#getModificationType(org.thymeleaf.Arguments, org.thymeleaf.dom.Element, java.lang.String, java.lang.String)
	 */
	@Override
	protected ModificationType getModificationType(Arguments arguments,
			Element element, String attributeName, String newAttributeName) {
		return ModificationType.APPEND_WITH_SPACE;
	}

	/**
	 * If this method is true , remove attribute.
	 * @see org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#removeAttributeIfEmpty(org.thymeleaf.Arguments, org.thymeleaf.dom.Element, java.lang.String, java.lang.String)
	 */
	@Override
	protected boolean removeAttributeIfEmpty(Arguments arguments,
			Element element, String attributeName, String newAttributeName) {
		return true;
	}

	/**
	 * Attribute write precedence, default value is 1000.
	 * @see org.thymeleaf.processor.AbstractProcessor#getPrecedence()
	 */
	@Override
	public int getPrecedence() {
		return ATTR_PRECEDENCE;
	}

	/**
	 * If has field error, output field error from strus2-field-errors.
	 */
	@Override
    protected String getTargetAttributeValue(
            final Arguments arguments, final Element element, final String attributeName) {

		Object actobj = ActionContext.getContext().getActionInvocation().getAction();
		if ( actobj == null ) {
			return BLANK;
		}

		if (!( actobj instanceof ActionSupport )) {
			return BLANK;
		}

		ActionSupport action = (ActionSupport)actobj;
		if (! action.hasFieldErrors()) {
			return BLANK;
		}

		final String fieldName = element.getAttributeValue("name");
        if (StringUtils.isEmptyOrWhitespace(fieldName)) {
            return BLANK;
        }

		Map<String,List<String>> fieldErrors = action.getFieldErrors();

		List<String> targetFieldErrors = fieldErrors.get(fieldName);
		if ( targetFieldErrors.isEmpty() || targetFieldErrors.size() == 0 ) {
			return BLANK;
		}

		return super.getTargetAttributeValue(arguments, element, attributeName);
	}

	public static final String BLANK = "";
}
