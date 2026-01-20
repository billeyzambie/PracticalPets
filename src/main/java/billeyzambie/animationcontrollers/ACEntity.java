package billeyzambie.animationcontrollers;

import java.util.HashMap;

public interface ACEntity {
    HashMap<String, ACData> getACData();

    //Used for scaling certain pet hats
    float headSizeX();
    float headSizeY();
    float headSizeZ();
}