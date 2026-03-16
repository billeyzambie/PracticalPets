package billeyzambie.animationcontrollers;

import java.util.HashMap;

public interface ACEntity {
    HashMap<String, ACData> getACData();
    HashMap<String, BVCData> getBVCData();
}