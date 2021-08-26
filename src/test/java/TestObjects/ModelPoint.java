package TestObjects;

import java.awt.*;
import java.util.Objects;

public class ModelPoint {
    float x,y;
    transient Point asPointGenerated = null;
    public ModelPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public ModelPoint(){}

    Point asPoint(){
        if(asPointGenerated==null)
            asPointGenerated = new Point((int)x,(int)y);
        return asPointGenerated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelPoint)) return false;
        ModelPoint that = (ModelPoint) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0;
    }

}
