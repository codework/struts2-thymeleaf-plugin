/**
 *
 */
package org.codework.struts.plugins.thymeleaf.diarect;

import java.util.Map;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import com.opensymphony.xwork2.ActionContext;

/**
 * Thymeleaf extensions,field.
 * @author A-pZ
 *
 */
//@Slf4j
public class FieldAttrProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {
	public static final int ATTR_PRECEDENCE = 1010;
	public static final String ATTR_NAME = "value";

	public FieldAttrProcessor() {
		super(ATTR_NAME);
	}

	protected String getTargetAttributeValue(Arguments arguments, Element element, String attributeName)
	{
		String attributeValue = super.getTargetAttributeValue(arguments, element, attributeName);
		/*
		if (element.hasNormalizedAttribute(Attribute.getPrefixFromAttributeName(attributeName), ATTR_NAME)) {
			return attributeValue;
		}*/
		String name = element.getAttributeValueFromNormalizedName("name");
		String type = element.getAttributeValueFromNormalizedName("type");
		return processOverridesValue(name,attributeValue);
	}

	/**
	 * If Type-Convertion Error found at Struts2, overwrite request-parameter same name.
	 *
	 * @param name parameter-name
	 * @param value thymeleaf parameter-value
	 * @return request-parameter-value(if convertion error occurs,return from struts2 , not else thymeleaf.)
	 */
	protected String processOverridesValue(String name , String value) {
		ActionContext ctx = ActionContext.getContext();
		Map<Object ,Object> overrideMap = ctx.getValueStack().getExprOverrides();
		if ( overrideMap != null && !overrideMap.isEmpty()) {
			if (overrideMap.containsKey(name)) {
				// log.debug("  - hit override map:[" + name + "] - " + value);
				String convertionValue = (String)overrideMap.get(name);

				return convertionValue;
			}
		}
		return value;
	}

	@Override
	protected String getTargetAttributeName(Arguments paramArguments, Element paramElement, String paramString) {
		return ATTR_NAME;
	}

	@Override
	protected ModificationType getModificationType(Arguments paramArguments, Element paramElement, String paramString1, String paramString2) {
		return AbstractAttributeModifierAttrProcessor.ModificationType.SUBSTITUTION;
	}

	@Override
	protected boolean removeAttributeIfEmpty(Arguments paramArguments, Element paramElement, String paramString1, String paramString2) {
		return false;
	}

	@Override
	public int getPrecedence() {
		return ATTR_PRECEDENCE;
	}
}
