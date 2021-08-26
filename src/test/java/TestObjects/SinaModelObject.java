package TestObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SinaModelObject {
    public ArrayList<ModelPoint> points = new ArrayList<>();
    public HashMap<Integer,ModelPoint> map = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SinaModelObject)) return false;
        SinaModelObject that = (SinaModelObject) o;
        return Objects.equals(points, that.points) && Objects.equals(map, that.map);
    }
}
