/**
 *
 */
package org.codework.struts.plugins.thymeleaf.diarect;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Thymeleaf extensions,field.
 * @author A-pZ
 *
 */
public class FieldAttrProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {
	public static final int ATTR_PRECEDENCE = 1010;
	public static final String ATTR_NAME = "value";

	public FieldAttrProcessor() {
		super(ATTR_NAME);
	}

	protected String getTargetAttributeValue(Arguments arguments, Element element, String attributeName)
	{
		String attributeValue = super.getTargetAttributeValue(arguments, element, attributeName);

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
		ValueStack stack = ctx.getValueStack();
		Map<Object ,Object> overrideMap = stack.getExprOverrides();

		if ( overrideMap == null || overrideMap.isEmpty()) {
			return value;
		}

		if (! overrideMap.containsKey(name)) {
			return value;
		}


		String convertionValue = (String)overrideMap.get(name);

		// Struts2-Conponent is wrapped String quote, which erase for output value.
		String altString =  StringEscapeUtils.unescapeJava(convertionValue);
		altString = altString.substring(1, altString.length() -1);

		return altString;
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
