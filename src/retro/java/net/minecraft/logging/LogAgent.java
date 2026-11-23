package net.minecraft.logging;

import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.LogManager;
import space.libs.util.MappedName;

import java.util.logging.*;

public class LogAgent implements ILogAgent {

    public final org.apache.logging.log4j.Logger LOGGER;

    @MappedName("serverLogger")
    public final Logger field_98242_a;

    @MappedName("logFile")
    public final String field_98240_b;

    @MappedName("loggerName")
    public final String field_98241_c;

    @MappedName("loggerPrefix")
    public final String field_98239_d;

    public LogAgent(String name, String prefix, String file) {
        LOGGER = LogManager.getLogger("LogAgent-" + name);
        this.field_98242_a = Logger.getLogger(name);
        this.field_98241_c = name;
        this.field_98239_d = prefix;
        this.field_98240_b = file;
        this.func_98238_b();
    }

    @MappedName("setupLogger")
    public void func_98238_b() {
        for (Handler handler : this.field_98242_a.getHandlers()) {
            this.field_98242_a.removeHandler(handler);
        }
        /*LogFormatter logFormatter = new LogFormatter(this, null);
        try {
            FileHandler fileHandler = new FileHandler(this.field_98240_b, true);
            fileHandler.setFormatter(logFormatter);
            this.field_98242_a.addHandler(fileHandler);
        } catch (Exception exception) {
            LOGGER.warn("Failed to log " + this.field_98241_c + " to " + this.field_98240_b + ": " + exception);
        }*/
    }

    @Override
    public void func_98233_a(String msg) {
        LOGGER.info(msg);
    }

    @MappedName("getServerLogger")
    @Override
    public Logger func_120013_a() {
        return this.field_98242_a;
    }

    @Override
    public void func_98236_b(String msg) {
        LOGGER.warn(msg);
    }

    @Override
    public void func_98231_b(String msg, Object... format) {
        LOGGER.warn(String.format(msg, format));
    }

    @Override
    public void func_98235_b(String msg, Throwable t) {
        LOGGER.warn(msg, t);
    }

    @Override
    public void func_98232_c(String msg) {
        LOGGER.error(msg);
    }

    @Override
    public void func_98234_c(String msg, Throwable t) {
        LOGGER.error(msg, t);
    }

    @Override
    public void func_98230_d(String msg) {
        FMLLog.fine(msg);
    }

    @MappedName("getLoggerPrefix")
    public static String func_98237_a(LogAgent logAgent) {
        return logAgent.field_98239_d;
    }

}
