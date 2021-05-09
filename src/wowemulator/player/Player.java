/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.player;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dusko
 */
public class Player {

    private final Map<AccountDataType, AccountData> accountData = new HashMap<>();

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
}
