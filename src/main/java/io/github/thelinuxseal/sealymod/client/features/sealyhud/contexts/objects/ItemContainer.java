package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects;

import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public final class ItemContainer {

    private List<Item> items = new ArrayList<>();
    private final Item EMPTY_ITEM = new Item();

    public ItemContainer(){
        EMPTY_ITEM.set(net.minecraft.world.item.ItemStack.EMPTY);
    }
    public void set(List<Item> items) {
        this.items = items;
    }
    public void setFromPlayerInventory(Inventory inv) {
        int size = inv.getContainerSize();

        while (items.size() < size) {
            items.add(new Item());
        }

        for (int i = 0; i < size; i++) {
            items.get(i).set(inv.getItem(i));
        }

        if (items.size() > size) {
            items.subList(size, items.size()).clear();
        }
    }

    @ContextFunc(
            path = "ItemContainer().size()",
            name = "Size",
            desc = "Returns the number of slots.",
            returns = "int"
    )
    public int size() {
        return items.size();
    }

    @ContextFunc(
            path = "ItemContainer().get(int slot)",
            name = "Get Item",
            desc = "Returns the item in the given slot.",
            returns = "Item"
    )
    public Item get(int slot) {
        if (slot < 0 || slot >= items.size()) {
            return EMPTY_ITEM;
        }
        return items.get(slot);
    }

    @ContextFunc(
            path = "ItemContainer().emptySlots()",
            name = "Empty Slots",
            desc = "Returns the number of empty slots.",
            returns = "int"
    )
    public int emptySlots() {
        int count = 0;
        for (Item item : items) {
            if (item.empty()) {
                count++;
            }
        }
        return count;
    }

    @ContextFunc(
            path = "ItemContainer().filledSlots()",
            name = "Filled Slots",
            desc = "Returns the number of non-empty slots.",
            returns = "int"
    )
    public int filledSlots() {
        return size() - emptySlots();
    }
}