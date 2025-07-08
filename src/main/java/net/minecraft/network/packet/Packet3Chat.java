package net.minecraft.network.packet;

import net.minecraft.util.ChatMessageComponent;
import space.libs.util.MappedName;

@SuppressWarnings("unused")
public class Packet3Chat extends Packet {

    @MappedName("message")
    public String field_73476_b;

    @MappedName("isServer")
    public boolean field_73477_c;

    public Packet3Chat() {
        this.field_73477_c = true;
    }

    public Packet3Chat(ChatMessageComponent component) {
        this(component.func_111062_i());
    }

    public Packet3Chat(ChatMessageComponent component, boolean server) {
        this(component.func_111062_i(), server);
    }

    public Packet3Chat(String msg) {
        this(msg, true);
    }

    public Packet3Chat(String msg, boolean server) {
        this.field_73477_c = server;
        if (msg.length() > 32767) {
            msg = msg.substring(0, 32767);
        }
        this.field_73476_b = msg;
    }

}
