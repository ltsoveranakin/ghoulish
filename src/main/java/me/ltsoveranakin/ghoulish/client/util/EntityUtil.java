package me.ltsoveranakin.ghoulish.client.util;

import me.ltsoveranakin.ghoulish.client.misc.MCInst;
import net.minecraft.entity.Entity;

import java.util.*;

public class EntityUtil implements MCInst {

    @SuppressWarnings("unchecked")
    public static <T extends Entity> List<T> findEntities(Class<T> entityClass) {
        List<T> entities = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (entity.equals(mc.player)) continue;
            if (entityClass.isAssignableFrom(entity.getClass())) {
                entities.add((T) entity);
            }
        }
        return entities;
    }

//    public static <T extends Entity> T findEntity(Class<T> entityClass, TargetType targType) {
//        switch (targType) {
//            case CLOSEST -> {
//                return findClosest(entityClass, targType.inRange);
//            }
//
//            case FURTHEST -> {
//                return new EntityQuery<>()
//                        .byClass(entityClass)
//                        .furthest();
//            }
//        }
//
//        throw new RuntimeException("Unhandled targetType " + targType);
//    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T findClosest(Class<T> entityClass, float range) {
        for (Entity entity : mc.world.getEntities()) {
            if (entityClass.isAssignableFrom(entity.getClass()) && !entity.equals(mc.player) && entity.squaredDistanceTo(mc.player) <= range * range) {
                return (T) entity;
            }
        }
        return null;
    }

    public enum TargetType {
        CLOSEST,
        FURTHEST;

        private int inRange = -1;

        public TargetType withinRange(int range) {
            inRange = range;
            return this;
        }
    }
}
