package spacebuilder2020.random_enderio_addons.conduits;

import crazypants.enderio.conduit.AbstractConduit;
import crazypants.enderio.conduit.AbstractConduitNetwork;
import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.TileConduitBundle;
import crazypants.enderio.conduit.geom.CollidableComponent;
import crazypants.enderio.conduit.power.IPowerConduit;
import crazypants.enderio.config.Config;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import spacebuilder2020.random_enderio_addons.Random_enderio_addons;

public class EUConduit extends AbstractConduit implements IEUConduit {

	private ConduitDelegate cD = null;
	
	private World w;
	private int xCoord, yCoord, zCoord;
	@Override
	public void onAddedToBundle()
	{
		super.onAddedToBundle();
		RegisterWithNetwork();
	}
	
	@Override
	public void updateEntity(World w)
	{
		super.updateEntity(w);
		if (cD == null && !w.isRemote)
		{
			RegisterWithNetwork();
		}
		
	}
	
	@Override
	public void onChunkUnload(World w)
	{
		super.onChunkUnload(w);
		if (cD == null && !w.isRemote)
		{
			RegisterWithNetwork();
		}
	}
	public void RegisterWithNetwork()
	{
		if (cD == null) //Only Register once
		{
			try {
				cD = new ConduitDelegate();
				TileEntity bundletE = ((TileEntity)AbstractConduit.class.getDeclaredField("bundle").get(this));
				
				cD.xCoord = bundletE.xCoord;
				cD.yCoord = bundletE.yCoord;
				cD.zCoord = bundletE.zCoord;
				xCoord = bundletE.xCoord;
				yCoord = bundletE.yCoord;
				zCoord = bundletE.zCoord;
				cD.setWorldObj(bundletE.getWorldObj());
				cD.parent = this;
				this.w = bundletE.getWorldObj();
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(cD));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void UnRegisterWithNetwork()
	{
		if (cD != null)
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(cD));
	}
	@Override
	public void onRemovedFromBundle()
	{
		super.onRemovedFromBundle();
		UnRegisterWithNetwork();
		
	}
	public class ConduitDelegate extends TileEntity implements IEnergyConductor
	{
		EUConduit parent;

		@Override
		public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public double getConductionLoss() {
			// TODO Auto-generated method stub
			return EUConduit.lossForTier[parent.tier];
		}

		@Override
		public double getInsulationEnergyAbsorption() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getInsulationBreakdownEnergy() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getConductorBreakdownEnergy() {
			// TODO Auto-generated method stub
			return EUConduit.energyForTier[parent.tier];
		}

		@Override
		public void removeInsulation() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeConductor() {
			// TODO Auto-generated method stub
			
		}
		
	}
	static int[] energyForTier = {8,32,128,512,2048,8192};
	static int[] ampsForTier = {2,4,8,16,32,64};
	static double[] lossForTier = {0.0125, 0.025, 0.2, 0.4, 0.8, 0.025};
	public EUConduit()
	{
		this(0);
	}
	public EUConduit(int tier)
	{
		this.tier = tier;
		this.amps = ampsForTier[tier];
		
	}
	
	
	double EU_Stored = 0;
	int tier;
	int amps = 1;
	EUConduitNetwork net = null;
	
	@Override
	public void readFromNBT(NBTTagCompound nbtRoot, short nbtVersion)
	{
		 super.readFromNBT(nbtRoot, nbtVersion);
		 EU_Stored = nbtRoot.getDouble("EU_Stored");
		 tier = nbtRoot.getInteger("tier");
		 amps = nbtRoot.getInteger("amps");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtRoot)
	{
		 super.writeToNBT(nbtRoot);
		 nbtRoot.setDouble("EU_Stored", EU_Stored);
		 nbtRoot.setInteger("tier", tier);
		 nbtRoot.setInteger("amps", amps);
	}
	@Override
	public Class<? extends IConduit> getBaseConduitType() {
		return IEUConduit.class;
	}

	@Override
	public ItemStack createItem() {
		return new ItemStack(Random_enderio_addons.iEU,1,0);
	}

	@Override
	public AbstractConduitNetwork<?, ?> getNetwork() {
		return net;
	}

	@Override
	public boolean setNetwork(AbstractConduitNetwork<?, ?> network) {
		if (network instanceof EUConduitNetwork)
		{
			return (net = (EUConduitNetwork) network) != null;
		}
		return false;
	}

	@Override
	public IIcon getTextureForState(CollidableComponent component) {
		ItemStack it = getBlockForTier(tier);
		return StackUtil.getBlock(it).getIcon(0, it.getItemDamage());
	}
	
	public ItemStack getBlockForTier(int tier)
	{
		
		switch (tier)
		{
		case 0:
			return Ic2Items.leadBlock;
		case 1:
			return Ic2Items.tinCableBlock;
		case 2:
			return Ic2Items.copperCableBlock;
		case 3:
			return Ic2Items.goldCableBlock;
		case 4:
			return Ic2Items.ironCableBlock;
		case 5:
			return Ic2Items.glassFiberCableBlock;
		default:
			return null;
		}
	}

	@Override
	public IIcon getTransmitionTextureForState(CollidableComponent component) {
		return null;
	}
	
	public static Class<? extends AbstractConduitNetwork> getNetworkClass()
	{
		return EUConduitNetwork.class;		
	}

	boolean isFull()
	{
		return EU_Stored >= amps*energyForTier[tier];
	}
	
	@Override
	public boolean canConnectToConduit(ForgeDirection direction, IConduit conduit) {
		/*boolean res = super.canConnectToConduit(direction, conduit);
	    if(!res) {
	      return false;
	    }
	    if(conduit instanceof EUConduit) {
	      return ((EUConduit) conduit).tier == tier;
	    }
	    return false;*/
		return super.canConnectToConduit(direction, conduit);
		
	}
	
	@Override
	public boolean canConnectToExternal(ForgeDirection direction, boolean ignoreDisabled) {
		if (ignoreDisabled)
			return false;
		else
		{
			if (w != null)
			{
				int x = direction.offsetX + xCoord, y = direction.offsetY + yCoord, z = direction.offsetZ + zCoord;
				TileEntity te = w.getTileEntity(x, y, z);
				if (te != null && te instanceof IEnergyTile)
					return true;
			}
			return false;
		}
		
		
	}

}
