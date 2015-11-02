package spacebuilder2020.random_enderio_addons;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import crazypants.enderio.EnderIO;
import crazypants.enderio.material.Alloy;
import crazypants.enderio.material.Material;
import crazypants.enderio.material.MaterialRecipes;
import crazypants.enderio.nei.AlloySmelterRecipeHandler.AlloySmelterRecipe;
import ic2.core.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import spacebuilder2020.random_enderio_addons.conduits.ItemEUConduit;

public class CommonProxy {

	
    public void preInit(FMLPreInitializationEvent e) {
    	Random_enderio_addons.iEU = ItemEUConduit.create();
    	
    }

    public void init(FMLInitializationEvent e) {
    	GameRegistry.addShapedRecipe(new ItemStack(Random_enderio_addons.iEU,2,0), new Object[]{
    	    	"CCC",
    	    	"AVA",
    	    	"CCC",
    	    	'A', Ic2Items.casinglead , 'C', Material.CONDUIT_BINDER.getStack(),'V', Items.iron_ingot
    	});
    	GameRegistry.addShapedRecipe(new ItemStack(Random_enderio_addons.iEU,2,1), new Object[]{
    	    	"CCC",
    	    	"AVA",
    	    	"CCC",
    	    	'A', Ic2Items.tinCableItem, 'C', Material.CONDUIT_BINDER.getStack(),'V', Alloy.ELECTRICAL_STEEL.getStackIngot()
    	});
    	GameRegistry.addShapedRecipe(new ItemStack(Random_enderio_addons.iEU,2,2), new Object[]{
    	    	"CCC",
    	    	"AVA",
    	    	"CCC",
    	    	'A', Ic2Items.copperCableItem, 'C', Material.CONDUIT_BINDER.getStack(),'V', Alloy.CONDUCTIVE_IRON.getStackIngot()
    	});
    	GameRegistry.addShapedRecipe(new ItemStack(Random_enderio_addons.iEU,2,3), new Object[]{
    	    	"CCC",
    	    	"AVA",
    	    	"CCC",
    	    	'A', Ic2Items.goldCableItem, 'C', Material.CONDUIT_BINDER.getStack(),'V', Alloy.ENERGETIC_ALLOY.getStackIngot()
    	});
    	GameRegistry.addShapedRecipe(new ItemStack(Random_enderio_addons.iEU,2,4), new Object[]{
    	    	"CCC",
    	    	"AVA",
    	    	"CCC",
    	    	'A', Ic2Items.ironCableItem, 'C', Material.CONDUIT_BINDER.getStack(),'V', Alloy.PHASED_GOLD.getStackIngot()
    	});
    	GameRegistry.addShapedRecipe(new ItemStack(Random_enderio_addons.iEU,2,5), new Object[]{
    	    	"CCC",
    	    	"AVA",
    	    	"CCC",
    	    	'A', Ic2Items.glassFiberCableItem, 'C', Material.CONDUIT_BINDER.getStack(),'V', Material.VIBRANT_CYSTAL.getStack()
    	});
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
