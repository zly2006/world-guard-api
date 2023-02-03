package com.github.zly2006.worldguard;

import com.github.zly2006.worldguard.event.Event;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Pair;

import java.util.*;

public class WorldGuardDispatcher {
    static Map<Class<? extends Event>, List<Pair<EventListener<?>, Integer>>> listeners = new HashMap<>();
    public interface EventListener<T extends Event> {
        ActionResult onEvent(T event);
        @SuppressWarnings("unchecked")
        default ActionResult onDispatch(Event event) {
            return onEvent((T) event);
        }
    }

    public static ActionResult dispatch(Event event) {
        List<Pair<EventListener<?>, Integer>> list = listeners.get(event.getClass());
        if (list != null) {
            for (Pair<EventListener<?>, Integer> listener : list) {
                ActionResult result = listener.getLeft().onDispatch(event);
                if (result != ActionResult.PASS) {
                    return result;
                }
            }
        }
        return ActionResult.PASS;
    }

    public static boolean shouldPrevent(Event event) {
        return dispatch(event) == ActionResult.FAIL;
    }

    public static <T extends Event>
    void register(EventListener<T> listener, Class<T> clazz, int priority) {
        List<Pair<EventListener<?>, Integer>> list = listeners.computeIfAbsent(clazz, k -> new ArrayList<>());
        list.add(new Pair<>(listener, priority));
        list.sort(Comparator.comparingInt(Pair::getRight));
    }

    public static <T extends Event>
    void register(EventListener<T> listener, Class<T> clazz) {
        register(listener, clazz, 0);
    }

    static void init() {

    }
}