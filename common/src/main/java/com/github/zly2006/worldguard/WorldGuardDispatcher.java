package com.github.zly2006.worldguard;

import com.github.zly2006.worldguard.event.Event;
import net.minecraft.util.ActionResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldGuardDispatcher {
    static Map<Class<Event>, List<EventListener<?>>> listeners = new HashMap<>();
    public interface EventListener<T extends Event> {
        ActionResult onEvent(T event);
        @SuppressWarnings("unchecked")
        default ActionResult onDispatch(Event event) {
            return onEvent((T) event);
        }
    }

    public static ActionResult dispatch(Event event) {
        listeners.get(event.getClass()).forEach(listener -> {
            listener.onDispatch(event);
        });
        return ActionResult.PASS;
    }

    public static boolean shouldPrevent(Event event) {
        return dispatch(event) == ActionResult.FAIL;
    }

    public static <T extends Event>
    void register(EventListener<T> listener) {

    }

    static void init() {

    }
}