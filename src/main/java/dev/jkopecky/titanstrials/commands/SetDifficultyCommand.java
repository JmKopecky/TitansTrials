package dev.jkopecky.titanstrials.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dev.jkopecky.titanstrials.data.DifficultyCapability;
import dev.jkopecky.titanstrials.data.DifficultyCapabilityProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class SetDifficultyCommand {

    //return the command itself
    public static LiteralArgumentBuilder<CommandSourceStack> get() {

        return Commands.literal("setdifficulty")
                .executes((CommandContext<CommandSourceStack> context) ->
                        run(context, -1))
                //todo constants defining max and min difficulty
                .then(Commands.argument("difficulty", IntegerArgumentType.integer())
                        .executes((CommandContext<CommandSourceStack> context) ->
                        run(context, IntegerArgumentType.getInteger(context, "difficulty"))));
    }

    //define what the command does
    public static int run(CommandContext<CommandSourceStack> context, int difficulty) {
        TextComponent errorMessage = new TextComponent("Invalid difficulty");
        CommandSyntaxException toThrow = new CommandSyntaxException(new SimpleCommandExceptionType(errorMessage), errorMessage);
        try {
            //todo replace with bounds check
            if (difficulty == -1) {
                throw toThrow;
            }

            //get player
            ServerPlayer player = context.getSource().getPlayerOrException();

            //get capability optional
            Optional<DifficultyCapability> cap = player.getCapability
                    (DifficultyCapabilityProvider.difficulty).resolve();

            //attempt to access the capability and set difficulty
            if (cap.isPresent()) {
                //set the difficulty
                cap.get().setDifficulty(difficulty);

                //notify user
                TextComponent response = new TextComponent(
                        "Your difficulty has been set to " + difficulty);
                context.getSource().sendSuccess(response, false);
            } else {
                throw toThrow;
            }
        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(new TextComponent("Invalid difficulty!"));
        }

        return 0;
    }
}
