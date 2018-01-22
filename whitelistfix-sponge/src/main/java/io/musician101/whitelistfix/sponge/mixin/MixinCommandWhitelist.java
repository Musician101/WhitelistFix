package io.musician101.whitelistfix.sponge.mixin;

import io.musician101.whitelistfix.Reference;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandWhitelist;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.whitelist.WhitelistService;
import org.spongepowered.api.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = CommandWhitelist.class, priority = 1001)
public abstract class MixinCommandWhitelist {

    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;setWhiteListEnabled(Z)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    public void on(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) {
        sender.sendMessage(new TextComponentString(args[0]));
    }

    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;setWhiteListEnabled(Z)V", ordinal = 1), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    public void off(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) {
        sender.sendMessage(new TextComponentString(args[0]));
    }

    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;removePlayerFromWhitelist(Lcom/mojang/authlib/GameProfile;)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    public void remove(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) {
        Sponge.getServer().getPlayer(args[1]).ifPresent(player -> player.kick(Text.of(Reference.KICK_REASON)));
    }

    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;reloadWhitelist()V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    public void reload(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) {
        sender.sendMessage(new TextComponentString(args[0]));
    }

    private void kickPlayers() {
        Sponge.getServiceManager().provide(WhitelistService.class).ifPresent(whitelistService -> {
            Sponge.getServer().getOnlinePlayers().stream().filter(player -> whitelistService.isWhitelisted(player.getProfile())).forEach(player -> {
                player.kick(Text.of(Reference.KICK_REASON));
            });
        });
    }
}
