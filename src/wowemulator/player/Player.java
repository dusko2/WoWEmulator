/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import wowemulator.player.character.PlayerCharacter;

/**
 *
 * @author Dusko
 */
public class Player {

    private final List<PlayerCharacter> characters = new LinkedList<>();
    
    public final void createdNewCharacter(PlayerCharacter character) {
        characters.add(character);
    }

    public List<PlayerCharacter> getCharacters() {
        return Collections.unmodifiableList(characters);
    }
}
