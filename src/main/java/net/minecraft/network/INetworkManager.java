package net.minecraft.network;

import net.minecraft.network.packet.NetHandler;

import java.net.SocketAddress;

public interface INetworkManager {

    void func_74425_a(NetHandler handler);

    void func_74429_a(Packet packet);

    void func_74427_a();

    void func_74428_b();

    SocketAddress func_74430_c();

    void func_74423_d();

    int func_74426_e();

    void func_74424_a(String s, Object... args);

    void func_74431_f();

}
