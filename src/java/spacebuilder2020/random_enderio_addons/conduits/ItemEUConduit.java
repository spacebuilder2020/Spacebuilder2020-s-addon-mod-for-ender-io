package spacebuilder2020.random_enderio_addons.conduits;

import crazypants.enderio.ModObject;
import crazypants.enderio.conduit.AbstractItemConduit;
import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.ItemConduitSubtype;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ItemEUConduit extends AbstractItemConduit {

	public static final ModObject itemEUConduit = EnumHelper.addEnum(ModObject.class, "itemEUConduit", new Class<?>[0], new Object[0]);
	public static ItemEUConduit create() {
		ItemEUConduit result = new ItemEUConduit();
	    result.init();
	    return result;
	  }
	static ItemConduitSubtype[] subtypes = new ItemConduitSubtype[] {
			  new ItemConduitSubtype("itemT0EUConduit", "rea:itemT0EUConduit"),
		      new ItemConduitSubtype("itemT1EUConduit", "rea:itemT1EUConduit"),
		      new ItemConduitSubtype("itemT2EUConduit", "rea:itemT2EUConduit"),
		      new ItemConduitSubtype("itemT3EUConduit", "rea:itemT3EUConduit"),
		      new ItemConduitSubtype("itemT4EUConduit", "rea:itemT4EUConduit"),
		      new ItemConduitSubtype("itemT5EUConduit", "rea:itemT5EUConduit"),
		  };
	protected ItemEUConduit() {
		super(itemEUConduit, subtypes);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends IConduit> getBaseConduitType() {
		// TODO Auto-generated method stub
		return IEUConduit.class;
	}

	@Override
	public IConduit createConduit(ItemStack item, EntityPlayer player) {
		// TODO Auto-generated method stub
		return new EUConduit(item.getItemDamage());
	}

	@Override
	public boolean shouldHideFacades(ItemStack stack, EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

}
