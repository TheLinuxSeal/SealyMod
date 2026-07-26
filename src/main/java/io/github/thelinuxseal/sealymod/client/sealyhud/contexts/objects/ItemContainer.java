package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects;

import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

import java.util.List;

public final class ItemContainer {

    private List<Item> items;

    public void set(List<Item> items) {
        this.items = items;
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
            return new Item(net.minecraft.world.item.ItemStack.EMPTY);
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