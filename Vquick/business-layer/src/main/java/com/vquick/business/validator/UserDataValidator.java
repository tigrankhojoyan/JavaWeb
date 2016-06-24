package com.vquick.business.validator;

import com.vquick.business.exception.BusinessException;
import com.vquick.dao.data.UserData;

/**
 * Created by tigran on 4/30/16.
 */
final class UserDataValidator {

    public static void validate(final UserData userData) throws BusinessException{
        validateFullName(userData);
        validateAddress(userData);
        validateAge(userData);
    }

    private static void validateFullName(final UserData userData) throws BusinessException {

    }

    private static void validateAddress(final UserData userData) throws BusinessException {

    }

    private static void validateAge(final UserData userData) throws BusinessException {

    }
}
