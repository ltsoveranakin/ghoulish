package me.ltsoveranakin.ghoulish.client.features.modules.conglomeration;

import me.ltsoveranakin.ghoulish.client.features.modules.conglomeration.conglomeration.AttackHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConglomerationManager {
    public static final List<Conglomeration> CONGLOMERATIONS = new ArrayList<>();
    public static final List<String> CONGLOMERATION_NAMES = new ArrayList<>();


    public static void init() {
        add(new AttackHelper());
    }

    @Nullable
    public static Conglomeration getConglomeration(String name) {
        for (Conglomeration conglomeration : CONGLOMERATIONS) {
            if (conglomeration.getName().equals(name)) {
                return conglomeration;
            }
        }
        return null;
    }


    private static void add(Conglomeration conglomeration) {
        CONGLOMERATIONS.add(conglomeration);
        CONGLOMERATION_NAMES.add(conglomeration.getName());
    }
}
