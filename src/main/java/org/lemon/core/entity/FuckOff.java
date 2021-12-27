package org.lemon.core.entity;

import java.util.Objects;

public class FuckOff {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FuckOffBuilder builder() {
        return new FuckOffBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuckOff fuckOff = (FuckOff) o;
        return Objects.equals(message, fuckOff.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "FuckOff{" +
                "message='" + message + '\'' +
                '}';
    }

    public static class FuckOffBuilder {
        private FuckOff obj = new FuckOff();

        public FuckOffBuilder message(String message) {
            obj.setMessage(message);
            return this;
        }

        public FuckOff build() {
            return this.obj;
        }
    }
}
