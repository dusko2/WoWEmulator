/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.realm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Dusko
 */
public class Realmlist implements Iterable<Realm> {

    private final List<Realm> realms = new LinkedList<>();

    private Realmlist() { }

    public final void load() {
        realms.add(new Realm(1, "Skyfire MoP", "127.0.0.1", 15015));
    }

    public final int count() {
        return realms.size();
    }

    public final short size() {
        short sum = 0;
        for (Realm realm : realms) {
            sum += realm.size();
        }
        return sum;
    }

    public final Realm get(int index) {
        return realms.get(index);
    }

    @Override public Iterator<Realm> iterator() {
        return realms.iterator();
    }

    @Override public void forEach(Consumer<? super Realm> consumer) {
        realms.forEach(consumer);
    }

    private static class RealmlistInstance {

        public static final Realmlist instance = new Realmlist();
    }

    public static Realmlist getInstance() {
        return RealmlistInstance.instance;
    }
}
