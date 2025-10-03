package dev.jkopecky.titanstrials.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.jkopecky.titanstrials.data.DifficultyCapability;
import dev.jkopecky.titanstrials.data.DifficultyCapabilityProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class GetDifficultyCommand {

    //return the command itself
    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal("getdifficulty").executes(GetDifficultyCommand::run);
    }

    //define what the command does
    public static int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        //get player
        ServerPlayer player = context.getSource().getPlayerOrException();

        //get capability optional
        Optional<DifficultyCapability> cap = player.getCapability
                (DifficultyCapabilityProvider.difficulty).resolve();

        //attempt to get difficulty
        if (cap.isPresent()) {
            int difficulty = cap.get().getDifficulty();
            TextComponent response = new TextComponent("Your difficulty is currently " + difficulty);
            context.getSource().sendSuccess(response, false);
        } else {
            TextComponent errorMessage = new TextComponent("An internal error occurred: player difficulty not stored.");
            context.getSource().sendFailure(errorMessage);
        }

        return 0;
    }
}
