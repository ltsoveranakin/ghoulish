package me.ltsoveranakin.ghoulish.client.event.sub.interfaces;

public interface ISubKey extends ISubscription {
    void onKey(long window, int key, int scancode, int action, int modifier);
}
