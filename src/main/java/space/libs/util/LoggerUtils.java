package space.libs.util;

import cpw.mods.fml.common.FMLLog;

import java.util.logging.*;

import static org.apache.logging.log4j.Level.*;

@SuppressWarnings("unused")
public class LoggerUtils extends Logger {

    public static final Logger INSTANCE = new LoggerUtils("FMLLog", null);

    public static org.apache.logging.log4j.Logger LOGGER = FMLLog.getLogger();

    public LoggerUtils(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    @Override
    public void log(Level level, String msg) {
        LOGGER.log(toLevel(level), msg);
    }

    @Override
    public void log(Level level, String msg, Object param) {
        LOGGER.log(toLevel(level), msg, param);
    }

    @Override
    public void log(Level level, String msg, Object[] params) {
        LOGGER.log(toLevel(level), msg, params);
    }

    @Override
    public void log(Level level, String msg, Throwable thrown) {
        LOGGER.log(toLevel(level), msg, thrown);
    }

    @Override
    public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
        LOGGER.error(sourceClass, sourceMethod, thrown);
    }

    @Override
    public void severe(String msg) {
        LOGGER.error(msg);
    }

    @Override
    public void warning(String msg) {
        LOGGER.warn(msg);
    }

    @Override
    public void info(String msg) {
        LOGGER.info(msg);
    }

    @Override
    public void config(String msg) {
        LOGGER.info(msg);
    }

    @Override
    public void fine(String msg) {
        LOGGER.debug(msg);
    }

    @Override
    public void finer(String msg) {
        LOGGER.debug(msg);
    }

    @Override
    public void finest(String msg) {
        LOGGER.trace(msg);
    }

    public static org.apache.logging.log4j.Level toLevel(Level level) {
        int i = level.intValue();
        if (i == Integer.MAX_VALUE) {
            return OFF;
        } else if (i > 900) {
            return ERROR;
        } else if (i > 800) {
            return WARN;
        } else if (i > 600) {
            return INFO;
        } else if (i > 400) {
            return DEBUG;
        } else if (i > 200) {
            return TRACE;
        } else {
            return ALL;
        }
    }
}
