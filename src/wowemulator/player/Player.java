/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import wowemulator.player.character.PlayerCharacter;

/**
 *
 * @author Dusko
 */
public class Player {

    private final Map<Long, PlayerCharacter> characters = new HashMap<>();
    
    public final void createdNewCharacter(PlayerCharacter character) {
        characters.put(character.guid, character);
    }

    public List<PlayerCharacter> getCharacters() {
        return Collections.unmodifiableList(new LinkedList<>(characters.values()));
    }
    
    public final PlayerCharacter find(long guid) {
        return characters.get(guid);
    }
}
