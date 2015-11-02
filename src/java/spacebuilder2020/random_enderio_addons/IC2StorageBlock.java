package spacebuilder2020.random_enderio_addons;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IC2StorageBlock extends BlockContainer {

	protected IC2StorageBlock(Material mat) {
		super(mat);
		setBlockName("IC2Storage");
		setCreativeTab(CreativeTabs.tabBlock);
		setHarvestLevel("pickaxe",2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World w, int dmg)
	{
		return new IC2StorageTile(dmg);
	}
}
