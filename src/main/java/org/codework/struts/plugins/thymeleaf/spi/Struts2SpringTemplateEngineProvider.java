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
package org.codework.struts.plugins.thymeleaf.spi;

import org.codework.struts.plugins.thymeleaf.diarect.FieldDiarect;

/**
 * Struts2-Spring4-Thymeleaf implementation.{@link TemplateEngineProvider}.
 *
 * @author Steven Benitez(Original Source) - overwrite by A-pZ ( 2.3.20 )
 *
 * @since 2.3.15
 * @version 2.3.20
 */
public class Struts2SpringTemplateEngineProvider extends DefaultTemplateEngineProvider {

	/**
	 * Configure settings from the struts.xml or struts.properties, using
	 * sensible defaults if values are not provided.
	 */
	public void configure() {
		super.configure();

		// add extended dialect.
		FieldDiarect fieldDiarect = new FieldDiarect();
		templateEngine.addDialect(fieldDiarect);
	}
}
