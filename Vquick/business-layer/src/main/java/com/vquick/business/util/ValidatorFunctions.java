package com.vquick.business.util;

import com.vquick.business.exception.BusinessException;
import com.vquick.business.manager.impl.UserManagerImpl;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tigran on 5/1/16.
 */
public class ValidatorFunctions {

    public static void validateDataByPattern(final String pattern, final String validationData) throws BusinessException{
        //TODO for blank data(StringUtils.isBlank().....)
        if(StringUtils.isBlank(validationData) || StringUtils.isBlank(pattern)) {
            throw new BusinessException(String.format("[Vquick][%s][validateDataByPattern] " +
                    "Failed to validate [%s] data by [%s] pattern(these data can't be null/empty).",
                    ValidatorFunctions.class.getSimpleName(), validationData, pattern));
        }
        Pattern validationPattern = Pattern.compile(pattern);;
        Matcher matcher = validationPattern.matcher(validationData);
        if(!matcher.matches()) {
            throw new BusinessException(String.format("[Vquick][%s][validateDataByPattern] Given [%s] data is not valid.", ValidatorFunctions.class.getSimpleName(), validationData));
        }
    }

}
