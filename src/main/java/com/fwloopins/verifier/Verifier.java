package com.fwloopins.verifier;

import com.fwloopins.verifier.command.UnverifyCommand;
import com.fwloopins.verifier.command.VerifyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Verifier extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
    }

    private void registerCommands() {
        getCommand("unverify").setExecutor(new UnverifyCommand());
        getCommand("verify").setExecutor(new VerifyCommand());
    }
}
