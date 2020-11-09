/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wowemulator.object.update;

import wowlib.utils.flag.BaseFlags;

/**
 *
 * @author Dusko
 */
public class UpdateFlags extends BaseFlags<UpdateFlag> {

    public UpdateFlags() {
        flags = UpdateFlag.None.rawValue();
    }
}
