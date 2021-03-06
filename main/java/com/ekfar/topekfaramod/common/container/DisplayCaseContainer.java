package com.ekfar.topekfaramod.common.container;

import java.util.Objects;

import com.ekfar.topekfaramod.common.te.DisplayCaseTileEntity;
import com.ekfar.topekfaramod.core.init.BlockInit;
import com.ekfar.topekfaramod.core.init.ContainerTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class DisplayCaseContainer extends Container{
	
	public final DisplayCaseTileEntity te;
	private final IWorldPosCallable canInteractWithCallable;

	public DisplayCaseContainer(final int windowId, final PlayerInventory playerInv, final DisplayCaseTileEntity te) {
		super(ContainerTypesInit.DISPLAY_CASE_CONTAINER_TYPE.get(),windowId);
		this.te = te;
		this.canInteractWithCallable = IWorldPosCallable.create(te.getLevel(),te.getBlockPos());
		//Tile Entity 
		this.addSlot(new Slot((IInventory) te, 0, 80, 35));
		
		//Main Player Inventory 
		for (int row = 0; row < 3; row++ ) {
			for (int col = 0; col < 9; col++) {
				this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 166 - (4 - row) * 18 - 10));
				
				
			}
		}
		//Player Hotbar
		for (int col = 0; col < 9; col++) {
			this.addSlot(new Slot (playerInv, col, 8 + col * 18, 142));
		}
	}

	
	public DisplayCaseContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
		this(windowId,playerInv, getTileEntity(playerInv, data));
	}
	private static DisplayCaseTileEntity getTileEntity(final PlayerInventory playerInv,final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "Player inventory cannot be now");
		Objects.requireNonNull(data, "Packet Buffer cannot be now");
		final TileEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if(te instanceof DisplayCaseTileEntity) {
			return(DisplayCaseTileEntity) te;
		}
		throw new IllegalStateException("tile entity is not correct");
	}
	
	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		//return stillValid(canInteractWithCallable, p_75145_1_, BlockInit.DISPLAY_CASE.get());
		return true;
	}
	@SuppressWarnings("unused")
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
	//private ItemStack transferStackInShot(PlayerEntity p_75145_1_,int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if(slot != null && slot.hasItem()) {
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			if(index < 36 && !this.moveItemStackTo(stack1, DisplayCaseTileEntity.slots, this.slots.size(), true)) {
				return ItemStack.EMPTY;
			}
			if(!this.moveItemStackTo(stack1, DisplayCaseTileEntity.slots, this.slots.size(), false)) {
				return ItemStack.EMPTY;
			}
			if(stack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			}else {
				slot.setChanged();
			}
		}
		return stack;

	}

}
