package net.minecraft.network;

import net.minecraft.network.packet.NetHandler;

import java.net.SocketAddress;

public interface INetworkManager {

    default void func_74425_a(NetHandler handler) {}

    default void func_74429_a(Packet packet) {}

    default void func_74427_a() {}

    default void func_74428_b() {}

    SocketAddress func_74430_c();

    default void func_74423_d() {}

    int func_74426_e();

    default void func_74424_a(String s, Object... args) {}

    default void func_74431_f() {}

}
