package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ExceptionRetryCall extends ExceptionMcoService {

    public final int field_148832_d;

    public ExceptionRetryCall(int p_i1146_1_) {
        super(503, "Retry operation", -1);
        this.field_148832_d = p_i1146_1_;
    }
}
