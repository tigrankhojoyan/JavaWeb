package com.vquick.dao.util;

import com.vquick.dao.exception.DaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tigran on 4/30/16.
 */
public class ConfigReader {

    private static final Log logger = LogFactory.getLog(ConfigReader.class);
    private String configFileName;

    public ConfigReader(String configFileName) {
        this.configFileName = configFileName;
    }

    public Properties getProperties() throws DaoException {
        Properties prop = new Properties();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                logger.error(String.format("[Vquick][%s][getProperties] The %s property file does not exist.", ConfigReader.class.getSimpleName(), configFileName));
                throw new DaoException(String.format("The %s property file does not exist.", configFileName));
            }
        } catch (IOException e) {
            logger.error(String.format("[Vquick][%s][getProperties] Failed to read properties from '%s' config file",
                    ConfigReader.class.getSimpleName(), configFileName), e);
            throw new DaoException(e);
        }
        return prop;
    }

}
