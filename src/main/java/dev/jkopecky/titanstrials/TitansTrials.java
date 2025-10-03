package dev.jkopecky.titanstrials;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod("titanstrials")
public class TitansTrials {

    //this must match the @Mod annotation
    public static final String MOD_ID = "titanstrials";

    //logging object
    private static final Logger LOGGER = LogUtils.getLogger();

    public TitansTrials()
    {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
