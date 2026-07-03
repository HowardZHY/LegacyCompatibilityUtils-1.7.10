package net.minecraft.network.packet;

import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import space.libs.fml.network.FMLNetworkHandler;
import space.libs.util.MappedName;

import java.io.*;

@SuppressWarnings("unused")
public class Packet1Login extends Packet {

    @MappedName("clientEntityIdf")
    public int field_73561_a;

    @MappedName("terrainType")
    public WorldType field_73559_b;

    @MappedName("hardcoreMode")
    public boolean field_73560_c;

    @MappedName("gameType")
    public WorldSettings.GameType field_73557_d;

    @MappedName("dimension")
    public int field_73558_e;

    @MappedName("difficultySetting")
    public byte field_73555_f;

    @MappedName("worldHeight")
    public byte field_73556_g;

    @MappedName("maxPlayers")
    public byte field_73562_h;

    public boolean vanillaCompatible;

    public Packet1Login() {
        this.vanillaCompatible = FMLNetworkHandler.vanillaLoginPacketCompatibility();
    }

    public Packet1Login(int entityID, WorldType worldType, WorldSettings.GameType gameMode, boolean hardcore, int dim, int difficulty, int height, int maxPlayers) {
        this.field_73561_a = entityID;
        this.field_73559_b = worldType;
        this.field_73558_e = dim;
        this.field_73555_f = (byte)difficulty;
        this.field_73557_d = gameMode;
        this.field_73556_g = (byte)height;
        this.field_73562_h = (byte)maxPlayers;
        this.field_73560_c = hardcore;
        this.vanillaCompatible = true;
    }

    @Override
    public void func_73267_a(DataInput input) throws IOException {}

    @Override
    public void func_73273_a(DataOutput output) throws IOException {}

    @Override
    public void func_73279_a(NetHandler handler) {
        handler.func_72455_a(this);
    }

    public int func_73284_a() {
        int i = 0;
        if (this.field_73559_b != null) {
            i = this.field_73559_b.getWorldTypeName().length();
        }
        return 6 + 2 * i + 4 + 4 + 1 + 1 + 1 + (vanillaCompatible ? 0 : 3);
    }
}
