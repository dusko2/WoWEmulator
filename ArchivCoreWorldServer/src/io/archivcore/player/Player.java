/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.player;

import io.archivcore.guid.ObjectGuid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import io.archivcore.player.character.PlayerCharacter;

/**
 *
 * @author Dusko
 */
public class Player {

    private final Map<AccountDataType, AccountData> accountData = new HashMap<>();
    private final Map<Long, PlayerCharacter> characters = new HashMap<>();

    public Player() {
        for (AccountDataType type : AccountDataType.values()) {
            accountData.put(type, new AccountData(type, 0, ""));
        }
    }

    public final void setAccountData(AccountData data) {
        accountData.put(data.type, data);
    }

    public final AccountData getAccountData(AccountDataType type) {
        return accountData.get(type);
    }

    public final void addNewCharacter(PlayerCharacter character) {
        characters.put(character.objectGuid.guid, character);
    }

    public final void deleteCharacter(ObjectGuid characterGuid) {
        characters.remove(characterGuid.guid);
    }

    public final Collection<PlayerCharacter> getAllCharacters() {
        return characters.values();
    }
}
