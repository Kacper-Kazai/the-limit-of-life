/*
 * The Limit of Life
 * Copyright (c) 2022 Kacper Kazai
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kazai.fabricmc.tlol.networking.packet;

import dev.kazai.fabricmc.tlol.configs.TLoLConfigs;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static dev.kazai.fabricmc.tlol.TLoL.MOD_ID;

public class ConfigSyncS2CPacket {
    public final static Identifier IDENTIFIER = new Identifier(MOD_ID, "config_sync");

    private static int maxLives = TLoLConfigs.DEFAULT.getMaxLives();
    public static int getMaxLives(){ return maxLives; }
    private static int defaultLives = TLoLConfigs.DEFAULT.getDefaultLives();
    public static int getDefaultLives(){ return defaultLives; }

    private static int laudanumUsageCooldown = TLoLConfigs.DEFAULT.getLaudanumUsageCooldown();
    public static int getLaudanumUsageCooldown(){ return laudanumUsageCooldown; }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender packetSender) {
        maxLives = buf.readInt();
        defaultLives = buf.readInt();
        laudanumUsageCooldown = buf.readInt();
    }
    public static void send(ServerPlayerEntity player, int maxLives, int defaultLives, int laudanumUsageCooldown){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(maxLives);
        buffer.writeInt(defaultLives);
        buffer.writeInt(laudanumUsageCooldown);
        ServerPlayNetworking.send(player, ConfigSyncS2CPacket.IDENTIFIER, buffer);
    }
}
