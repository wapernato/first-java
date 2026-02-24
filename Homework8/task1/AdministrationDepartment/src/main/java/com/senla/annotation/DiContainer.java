package com.senla.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DiContainer {

    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    /** Привязка интерфейса/абстракции к реализации */
    public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
        if (!abstraction.isAssignableFrom(implementation)) {
            throw new IllegalArgumentException(
                    implementation.getName() + " is not a " + abstraction.getName()
            );
        }
        bindings.put(abstraction, implementation);
    }

    /** Зарегистрировать уже созданный объект (например AppConfig) */
    public <T> void registerInstance(Class<T> type, T instance) {
        singletons.put(type, instance);
    }

    /** Получить объект из контейнера (создать + внедрить зависимости) */
    public <T> T get(Class<T> type) {
        return type.cast(resolve(type, new HashSet<>()));
    }

    /** Внедрить зависимости в уже созданный объект */
    public void configure(Object target) {
        injectInto(target, new HashSet<>());
    }

    // -------------------- internal --------------------

    private Object resolve(Class<?> type, Set<Class<?>> constructing) {
        // 1) если уже есть singleton — вернуть
        Object cached = singletons.get(type);
        if (cached != null) return cached;

        // 2) выбрать реальный класс для создания
        Class<?> impl = type;

        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            impl = bindings.get(type);
            if (impl == null) {
                throw new RuntimeException("No binding for: " + type.getName());
            }
        }

        // 3) защита от циклических зависимостей
        if (constructing.contains(impl)) {
            throw new RuntimeException("Circular dependency detected: " + impl.getName());
        }
        constructing.add(impl);

        // 4) создать инстанс
        Object instance = createInstance(impl);

        // 5) положить в кеш по ТИПУ, который запрашивали (обычно интерфейс)
        singletons.put(type, instance);
        singletons.put(impl, instance);

        // 6) внедрить зависимости внутрь созданного объекта
        injectInto(instance, constructing);

        constructing.remove(impl);
        return instance;
    }

    private Object createInstance(Class<?> impl) {
        try {
            Constructor<?> ctor = impl.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No default constructor for: " + impl.getName(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate: " + impl.getName(), e);
        }
    }

    private void injectInto(Object target, Set<Class<?>> constructing) {
        Class<?> clazz = target.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Inject.class)) continue;
            if (Modifier.isFinal(field.getModifiers())) continue;

            field.setAccessible(true);

            Class<?> depType = field.getType();

            if (depType.isPrimitive() || depType == String.class
                    || depType == Integer.class || depType == Long.class
                    || depType == Boolean.class || depType == Double.class) {
                throw new RuntimeException("Tried to inject value type into: "
                        + target.getClass().getName() + "." + field.getName()
                        + " (type=" + depType.getName() + "). Значения бери из AppConfig, не через @Inject");
            }

            Object dep = resolve(depType, constructing);

            try {
                field.set(target, dep);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                        "Can't inject " + depType.getName() + " into " + clazz.getName() + "." + field.getName(), e
                );
            }
        }
    }
}
