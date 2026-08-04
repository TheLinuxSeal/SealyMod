package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.system;

import net.minecraft.client.Minecraft;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

public final class SystemContext {

    private final OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
    private final MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
    @ContextFunc(path = "system.cpuUtil()", name = "CPU Utilization", desc = "Returns how much of the CPU is being used.", returns = "double")
    public double cpuUtil() {
        return os.getSystemLoadAverage();
    }

    @ContextFunc(path = "system.osArch()", name = "OS Architecture", desc = "Returns the OS architecture.", returns = "String")
    public String osArch() {
        return os.getArch();
    }

    @ContextFunc(path = "system.osName()", name = "OS Name", desc = "Returns the OS name.", returns = "String")
    public String osName(){
        return os.getName();
    }

    @ContextFunc(path = "system.osVersion()", name = "OS Version", desc = "Returns the OS version.", returns = "String")
    public String osVersion(){
        return os.getVersion();
    }

    @ContextFunc(path = "system.memUsed()", name = "Used Memory", desc = "Returns the amount of memory used.", returns = "double")
    public double memUsed() {
        return mem.getHeapMemoryUsage().getUsed();
    }
    @ContextFunc(path = "system.memMax()", name = "Maximum Memory", desc = "Returns the maximum amount of memory available.", returns = "double")
    public double memMax() {
        return mem.getHeapMemoryUsage().getMax();
    }
    @ContextFunc(path = "system.memMin()", name = "Initial Memory", desc = "Returns the amount of memory initially available.", returns = "double")
    public double memMin() {
        return mem.getHeapMemoryUsage().getInit();
    }
    @ContextFunc(path = "system.memCommited()", name = "Commited Memory", desc = "Returns the amount of memory commited.", returns = "double")
    public double memCommited() {
        return mem.getHeapMemoryUsage().getCommitted();
    }
    @ContextFunc(path = "system.gcCycles()", name = "GC Cycles", desc = "Returns the amount of garbage collection cycles.", returns = "long")
    public long gcCycles() {
        long count = 0;
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            count += gc.getCollectionCount();
        }
        return count;
    }

    @ContextFunc(path = "system.gcTime()", name = "GC Time", desc = "Returns the amount of total time spent on garbage collection, in milliseconds.", returns = "long")

    public long gcTime() {
        long time = 0;
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            time += gc.getCollectionTime();
        }
        return time;
    }

    @ContextFunc(path = "system.gpuUtil()", name = "GPU Utilization", desc = "Returns the GPU utilization.", returns = "double")
    public double gpuUtil(){
        return Minecraft.getInstance().getGpuUtilization();
    }
}