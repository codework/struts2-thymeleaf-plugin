/**
 *
 */
package org.codework.struts.plugins.thymeleaf.diarect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * diarect:sth.
 * @author A-pZ
 *
 */
public class FieldDiarect extends AbstractDialect {

	@Override
	public String getPrefix() {
		return "sth";
	}

	public Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new FieldAttrProcessor());
		return processors;
	}

}
