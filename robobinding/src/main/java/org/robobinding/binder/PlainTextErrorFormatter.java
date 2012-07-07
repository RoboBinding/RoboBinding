/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.binder;

import java.text.MessageFormat;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.robobinding.binder.BindingViewInflationErrorsException.ErrorFormatter;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PlainTextErrorFormatter implements ErrorFormatter
{
	@Override
	public String format(BindingViewInflationError inflationError)
	{
		StrBuilder errorMessageBuilder = new StrBuilder();
		
		errorMessageBuilder.appendln(MessageFormat.format("-------------------------{0}({1} errors)-----------------------", inflationError.getViewName(), inflationError.numErrors()));
		errorMessageBuilder.append(formatErrors(inflationError.getErrors()));
		
		return errorMessageBuilder.toString();
	}
	
	private String formatErrors(Collection<Exception> errors)
	{
		StrBuilder errorMessageBuilder = new StrBuilder();
		
		for(Exception error : errors)
		{
			errorMessageBuilder.appendln(error.toString());
			errorMessageBuilder.appendNewLine();
		}
		
		if(!errors.isEmpty())
		{
			removeEndNewLine(errorMessageBuilder);
		}
		
		return errorMessageBuilder.toString();
	}

	private void removeEndNewLine(StrBuilder sb)
	{
		int endIndex = sb.length();
		int startIndex = endIndex - lengthOfNewLineText(sb);
		sb.delete(startIndex, endIndex);
	}
	
	private int lengthOfNewLineText(StrBuilder sb)
	{
		String newLineText = StringUtils.defaultString(sb.getNewLineText(), SystemUtils.LINE_SEPARATOR);
		return newLineText.length();
	}

}
