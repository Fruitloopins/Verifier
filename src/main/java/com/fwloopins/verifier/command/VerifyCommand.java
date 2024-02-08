package com.fwloopins.verifier.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VerifyCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Component.text("No player specified", NamedTextColor.RED));
            return true;
        }

        OfflinePlayer player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(Component.text("Specified player does not exist", NamedTextColor.RED));
            return true;
        }

        NodeBuilder builder = Node.builder("group.builder").value(true);

        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if (user == null) {
            sender.sendMessage(Component.text("Specified LuckPerms user is null, are they online?", NamedTextColor.RED));
            return true;
        }

        user.data().add(builder.build());
        luckPerms.getUserManager().saveUser(user);

        sender.sendMessage(Component.text("Successfully set " + player.getName() + " as a builder", NamedTextColor.GREEN));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            completions.add(player.getName());
        }

        if (!args[0].isEmpty()) {
            for (int i = 0; i < completions.size(); i++) {
                String name = completions.get(i);
                if (!name.toLowerCase().startsWith(args[0].toLowerCase()))
                    completions.remove(name);
            }
        }

        return completions;
    }
}
