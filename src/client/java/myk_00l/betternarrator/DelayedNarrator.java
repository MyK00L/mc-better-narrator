package myk_00l.betternarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.lang.Runnable;

public class DelayedNarrator {
    private final int millis;
    private static ScheduledFuture<?> common_future;
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> future;
    public DelayedNarrator(int millis) {
        this.millis=millis;
    }
    public void say(Component component) {
        if(this.common_future != null && !this.common_future.isDone()) {
            this.common_future.cancel(false);
        }
        this.future = this.common_future = executor.schedule(new SayRunnable(component), millis, TimeUnit.MILLISECONDS);
    }
    public void cancel() {
        if(this.future != null && !this.future.isDone()) {
            this.future.cancel(false);
            this.future = null;
        }
    }
    private class SayRunnable implements Runnable {
        private final Component component;
        SayRunnable(Component component) {
            this.component = component;
        }
        public void run() {
            Minecraft.getInstance().getNarrator().saySystemNow(component);
        }
    }
}