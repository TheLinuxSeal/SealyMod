package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects;

import net.minecraft.world.item.ItemStack;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

import java.util.Locale;

public final class Item {

    private ItemStack stack;

    public Item(ItemStack stack) {
        this.stack = stack;
    }

    @ContextFunc(
            path = "Item().name()",
            name = "Item Name",
            desc = "Returns the display name of the item.",
            returns = "String"
    )
    public String name() {
        return stack.getHoverName().getString();
    }

    @ContextFunc(
            path = "Item().id()",
            name = "Item ID",
            desc = "Returns the registry ID of the item.",
            returns = "String"
    )
    public String id() {
        return stack.typeHolder().getRegisteredName().toLowerCase(Locale.ROOT);
    }

    @ContextFunc(
            path = "Item().count()",
            name = "Count",
            desc = "Returns the number of items in the stack.",
            returns = "int"
    )
    public int count() {
        return stack.getCount();
    }

    @ContextFunc(
            path = "Item().maxStackSize()",
            name = "Maximum Stack Size",
            desc = "Returns the maximum stack size.",
            returns = "int"
    )
    public int maxStackSize() {
        return stack.getMaxStackSize();
    }

    @ContextFunc(
            path = "Item().damage()",
            name = "Damage",
            desc = "Returns the current damage value.",
            returns = "int"
    )
    public int damage() {
        return stack.getDamageValue();
    }

    @ContextFunc(
            path = "Item().maxDamage()",
            name = "Maximum Damage",
            desc = "Returns the maximum durability.",
            returns = "int"
    )
    public int maxDamage() {
        return stack.getMaxDamage();
    }

    @ContextFunc(
            path = "Item().durability()",
            name = "Remaining Durability",
            desc = "Returns the remaining durability.",
            returns = "int"
    )
    public int durability() {
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    @ContextFunc(
            path = "Item().damageable()",
            name = "Damageable",
            desc = "Returns whether this item has durability.",
            returns = "boolean"
    )
    public boolean damageable() {
        return stack.isDamageableItem();
    }

    @ContextFunc(
            path = "Item().empty()",
            name = "Empty",
            desc = "Returns whether this item stack is empty.",
            returns = "boolean"
    )
    public boolean empty() {
        return stack.isEmpty();
    }
}