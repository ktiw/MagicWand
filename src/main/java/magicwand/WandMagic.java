package magicwand;


import org.bukkit.plugin.java.JavaPlugin;

public class WandMagic extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("givewand").setExecutor(new WandCommand());

        getServer().getPluginManager().registerEvents(new WandListener(), this);

    }
}