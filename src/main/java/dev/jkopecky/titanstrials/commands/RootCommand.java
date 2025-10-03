package dev.jkopecky.titanstrials.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.jkopecky.titanstrials.TitansTrials;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TitansTrials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RootCommand {

    @SubscribeEvent
    public static void RegisterCommandsEvent(RegisterCommandsEvent event) {
        registerRoot(event.getDispatcher());
    }

    //register all other commands for this mod
    public static void registerRoot(CommandDispatcher<CommandSourceStack> registrar) {
        //root command /titanstrials
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("titanstrials")
                .executes(RootCommand::help);

        root.then(SetDifficultyCommand.get());
        root.then(GetDifficultyCommand.get());

        registrar.register(root);
    }

    //provide the help message menu to the user
    public static int help(CommandContext<CommandSourceStack> context) {
        TextComponent message = new TextComponent("Titans Trials Help:");
        context.getSource().sendSuccess(message, false);
        return 0;
    }
}
