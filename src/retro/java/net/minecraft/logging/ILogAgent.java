package net.minecraft.logging;

import cpw.mods.fml.common.FMLLog;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public interface ILogAgent {

    default void func_98230_d(String msg) {
        FMLLog.fine("[ILogAgent] ".concat(msg));
    }

    default Logger func_120013_a() {
        return Logger.getLogger("ILogAgent");
    }

    void func_98233_a(String msg);

    void func_98236_b(String msg);

    void func_98231_b(String msg, Object... objects);

    void func_98235_b(String msg, Throwable t);

    void func_98232_c(String msg);

    void func_98234_c(String msg, Throwable t);

}
