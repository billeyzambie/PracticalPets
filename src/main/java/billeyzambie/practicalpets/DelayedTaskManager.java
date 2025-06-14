package billeyzambie.practicalpets;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.Queue;

@Mod.EventBusSubscriber
public class DelayedTaskManager {
    private static final Queue<DelayedTask> tasks = new LinkedList<>();

    public static void schedule(Runnable task, int delayTicks) {
        tasks.add(new DelayedTask(task, delayTicks));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tasks.removeIf(DelayedTask::tick);
        }
    }

    private static class DelayedTask {
        private final Runnable task;
        private int ticksRemaining;

        public DelayedTask(Runnable task, int delayTicks) {
            this.task = task;
            this.ticksRemaining = delayTicks;
        }

        public boolean tick() {
            if (--ticksRemaining <= 0) {
                task.run();
                return true;
            }
            return false;
        }
    }
}
