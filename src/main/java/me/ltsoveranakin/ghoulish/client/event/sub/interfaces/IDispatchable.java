package me.ltsoveranakin.ghoulish.client.event.sub.interfaces;

public interface IDispatchable {
    static boolean shouldSkip(Object o) {
        if (o instanceof IDispatchable dispatchable) {
            return dispatchable.shouldSkipDispatch();
        }

        return false;
    }

    boolean shouldSkipDispatch();
}
