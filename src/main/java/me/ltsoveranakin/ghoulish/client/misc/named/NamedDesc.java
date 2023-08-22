package me.ltsoveranakin.ghoulish.client.misc.named;

public class NamedDesc extends Named {
    private final String desc;

    public NamedDesc(String name, String desc) {
        super(name);
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
