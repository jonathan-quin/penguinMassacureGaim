package com.mygdx.game.helpers.utilities;

import java.util.ArrayList;

public interface TimeRewindInterface {



    public ArrayList<Object> save();

    public <T> T init();
    public <T> T load(Object... vars);

    public void setLastSave(ArrayList<Object> save);


}
