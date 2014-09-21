package org.idwfed.app.util;

import org.idwfed.app.domain.Item;

import java.util.List;

/**
 * Created by kevintanhongann on 9/20/14.
 */
public class LoginSuccessEvent {

    private List<Item> items;

    public LoginSuccessEvent(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "LoginSuccessEvent{" +
                "items=" + items +
                '}';
    }
}
