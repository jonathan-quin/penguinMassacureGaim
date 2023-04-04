package com.mygdx.game.helpers;

import java.util.ArrayList;
import java.util.HashMap;

public interface TimeRewindInterface {

    public ArrayList<Object> save();

    public <T> T init();
    public <T> T load(Object... vars);


}
