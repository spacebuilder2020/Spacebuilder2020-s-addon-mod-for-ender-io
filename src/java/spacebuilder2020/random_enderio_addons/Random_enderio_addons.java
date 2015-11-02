package spacebuilder2020.random_enderio_addons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import spacebuilder2020.random_enderio_addons.conduits.ItemEUConduit;

@Mod(modid = Random_enderio_addons.MODID, name=Random_enderio_addons.MODNAME, version = Random_enderio_addons.VERSION)
public class Random_enderio_addons {
	public static final String MODID = "rea";
	public static final String MODNAME = "Random Enderio Addons";
	public static final String VERSION = "0.1.0";
	
	public static ItemEUConduit iEU;
	
	@SidedProxy(clientSide="spacebuilder2020.random_enderio_addons.ClientProxy", serverSide="spacebuilder2020.random_enderio_addons.ServerProxy")
	public static CommonProxy proxy;
	
	@Instance
    public static Random_enderio_addons instance = new Random_enderio_addons();
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
    }
        
    @EventHandler
    public void init(FMLInitializationEvent e) {
    	proxy.init(e);       
    }
        
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	proxy.postInit(e);
    }
}
