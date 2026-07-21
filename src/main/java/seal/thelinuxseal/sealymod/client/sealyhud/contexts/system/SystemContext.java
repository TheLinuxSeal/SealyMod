package seal.thelinuxseal.sealymod.client.sealyhud.contexts.system;

import net.minecraft.client.Minecraft;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

public final class SystemContext {

    private final OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
    private final MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
    public double cpuUtil() {
        return os.getSystemLoadAverage();
    }

    public String osArch() {
        return os.getArch();
    }

    public String osName(){
        return os.getName();
    }

    public String osVersion(){
        return os.getVersion();
    }

    public double memUsed() {
        return mem.getHeapMemoryUsage().getUsed();
    }

    public double memMax() {
        return mem.getHeapMemoryUsage().getMax();
    }
    public double memMin() {
        return mem.getHeapMemoryUsage().getInit();
    }
    public double memCommited() {
        return mem.getHeapMemoryUsage().getCommitted();
    }

    public long gcCount() {
        long count = 0;
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            count += gc.getCollectionCount();
        }
        return count;
    }

    public long gcTimeMs() {
        long time = 0;
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            time += gc.getCollectionTime();
        }
        return time;
    }

    public double gpuUtil(){
        return Minecraft.getInstance().getGpuUtilization();
    }
}