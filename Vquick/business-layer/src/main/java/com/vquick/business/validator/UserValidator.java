package com.vquick.business.validator;

import com.vquick.business.exception.BusinessException;
import com.vquick.business.util.ValidatorFunctions;
import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;
import com.vquick.dao.util.ConfigReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class checks if the provided 'User' parameter contains valid data.
 */
public final class UserValidator {

    private static ConfigReader userPatternsConfigReader = new ConfigReader("userData.properties");
    private static final Log logger = LogFactory.getLog(UserValidator.class);
    private static String USER_NAME_PATTERN = "user.username.pattern";
    private static String PASSWORD_PATTERN = "user.password.pattern";

    public UserValidator() {

    }

    /**
     * Validates provided 'user' data.
     *
     * @param user
     * @throws BusinessException - if provided data is invalid.
     */
    public static void validate(final User user) throws BusinessException {
        validateUserName(user.getUserName());
        validatePassword(user.getPassword());
        UserDataValidator.validate(user.getUserData());
    }

    /**
     * Validates the 'userName' of the user.
     *
     * @param userName
     * @throws BusinessException
     */
    public static void validateUserName(final String userName) throws BusinessException {
        try {
            ValidatorFunctions.validateDataByPattern(
                    userPatternsConfigReader.getProperties().getProperty(USER_NAME_PATTERN), userName);
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e);
        }

    }

    /**
     * Validates the 'password' of the user.
     *
     * @param password
     * @throws BusinessException
     */
    public static void validatePassword(final String password) throws BusinessException {
        try {
            ValidatorFunctions.validateDataByPattern(
                    userPatternsConfigReader.getProperties().getProperty(PASSWORD_PATTERN), password);
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

}
