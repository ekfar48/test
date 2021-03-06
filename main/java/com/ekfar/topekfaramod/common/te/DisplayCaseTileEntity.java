package com.ekfar.topekfaramod.common.te;

import com.ekfar.topekfaramod.FirstEkfaraMod;
import com.ekfar.topekfaramod.common.container.DisplayCaseContainer;
import com.ekfar.topekfaramod.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DisplayCaseTileEntity extends LockableLootTileEntity{
	
	public static int slots = 1;
	
	protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

	protected DisplayCaseTileEntity(TileEntityType<?> p_i48285_1_) {
		super(p_i48285_1_);

	}
	public DisplayCaseTileEntity() {
		this(TileEntityTypesInit.DISPLAY_CASE_TILE_ENTITY_TYPE.get());
	}
	@Override
	public int getContainerSize() {
		return slots;
	}
	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}
	
	public ItemStack getItem() {
		return this.items.get(0);
	}
	
	
	
	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;
		
	}
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + FirstEkfaraMod.MOD_ID + ".display_case");
	}
	@Override
	protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
		return new DisplayCaseContainer(p_213906_1_, p_213906_2_, this);
	}
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		
		if(!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, this.items);
		}
		return compound;
	}
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state,nbt);
		this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		if (!this.trySaveLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.items);
		}
	}
}
