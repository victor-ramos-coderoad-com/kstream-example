package com.mojix.examples.commons.wrappers;

import java.util.Map;

/**
 * Created by dbascope on 1/3/17
 */
public class PropertyWrapper {
    public Map<String, ThingPropertyWrapper> current;

    public Map<String, ThingPropertyWrapper> previous;

    public PropertyWrapper() {
    }

    public PropertyWrapper(Map<String, ThingPropertyWrapper> current, Map<String, ThingPropertyWrapper> previous) {
        this.current = current;
        this.previous = previous;
    }

    public Map<String, ThingPropertyWrapper> getCurrent() {
        return current;
    }

    public void setCurrent(Map<String, ThingPropertyWrapper> current) {
        this.current = current;
    }

    public Map<String, ThingPropertyWrapper> getPrevious() {
        return previous;
    }

    public void setPrevious(Map<String, ThingPropertyWrapper> previous) {
        this.previous = previous;
    }
}
