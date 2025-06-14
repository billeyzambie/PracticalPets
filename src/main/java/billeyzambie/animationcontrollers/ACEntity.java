package billeyzambie.animationcontrollers;

import java.util.HashMap;

public interface ACEntity {
    HashMap<String, ACData> getACData();

    float headSizeX();
    float headSizeY();
    float headSizeZ();
}