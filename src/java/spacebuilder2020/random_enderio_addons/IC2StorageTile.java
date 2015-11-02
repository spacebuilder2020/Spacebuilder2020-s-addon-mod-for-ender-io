package spacebuilder2020.random_enderio_addons;

import java.util.logging.Level;
import java.util.logging.Logger;

import ic2.api.energy.event.*;
import ic2.api.energy.tile.*;
import ic2.core.*;
import ic2.core.block.TileEntityBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class IC2StorageTile extends TileEntityBlock implements IEnergySink, IEnergySource {

	int dmg = 0;
	boolean isinEnergyNet = false;
	public IC2StorageTile()
	{
		this(0);
	}
	public IC2StorageTile(int dmg)
	{
		super();
		this.dmg = dmg;
		
	}
	
	@Override
	public void onUnloaded()
	{
		super.onUnloaded();
		if (isinEnergyNet && IC2.platform.isSimulating())
		{
			Logger.getGlobal().log(Level.INFO, "Unloaded");
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			isinEnergyNet = false;
		}
		
	}
	
	@Override
	public void onLoaded()
	{
		super.onLoaded();
		if (IC2.platform.isSimulating())
		{
			Logger.getGlobal().log(Level.INFO, "Loaded");
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			isinEnergyNet = true;
		}
	}
	@Override
	public void updateEntityServer()
	{		
		super.updateEntityServer();
		if (energyOverflow > 0)
		{
			energyOverflow-=8*Math.pow(4, tier);
		}
	}
	double energyStored = 0;
	double energyOverflow = 0;
	double maxStored = 10*1024*1024;
	int tier = 4;
	
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		// Accepts energy from any side
		return true;
	}

	@Override
	public double getDemandedEnergy() {
		double maxamount = 8*Math.pow(4, tier);
		double demanded = maxStored - energyStored;
		return energyOverflow <= 0 ? demanded <= maxamount ? demanded : maxamount : 0;
		
	}

	
	@Override
	public int getSinkTier() {
		// Returns device tier
		return tier;
	}

	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		if (energyStored <= maxStored && energyOverflow <= 0)
		{
			double maxamount = 8*Math.pow(4, tier);
			double amps = amount / voltage;
			if (amount > maxamount)
			{
				energyOverflow = amount - maxamount;
			}
			energyStored += amount;
			return 0;
		}
		return amount;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound var)
	{
		var.setDouble("energyStored", energyStored);
		var.setDouble("maxStored", maxStored);
		var.setInteger("tier", tier);
		var.setInteger("dmg", dmg);
		super.writeToNBT(var);
	}

	@Override
	public void readFromNBT(NBTTagCompound var)
	{
	
		energyStored = var.getDouble("energyStored");
		maxStored = var.getDouble("maxStored");
		tier = var.getInteger("tier");
		dmg = var.getInteger("dmg");
		super.readFromNBT(var);
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public double getOfferedEnergy() {
		// TODO Auto-generated method stub
		return 8*Math.pow(4, tier);
	}

	@Override
	public void drawEnergy(double amount) {
		// TODO Auto-generated method stub
		
		energyStored -= amount;
	}

	@Override
	public int getSourceTier() {
		// TODO Auto-generated method stub
		return tier;
	}
}
