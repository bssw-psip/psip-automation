package io.bssw.psip.backend.service.events;

import org.springframework.context.ApplicationEvent;

public class AuthChangeEvent extends ApplicationEvent {
    public enum Type {
        LOGIN,
        LOGOUT
    }

    private final Type type;

    public AuthChangeEvent(Object source, Type type) {
        super(source);
        this.type = type;
    }
    
    public Type getType() {
        return type;
    }
}
