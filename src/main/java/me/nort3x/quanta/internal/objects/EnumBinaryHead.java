package me.nort3x.quanta.internal.objects;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;

public class EnumBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        int enumOrdinal = ds.readInt32();
        if (enumOrdinal == -1)
            return;
        Object[] enumConsts = f.getType().getEnumConstants();
        Object value = null;
        if (enumConsts != null && enumOrdinal < enumConsts.length)
            value = enumConsts[enumOrdinal];
        f.set(o, value);
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Object fValue = f.get(o);
        Object[] enumConsts = f.getType().getEnumConstants();
        if (enumConsts == null || fValue == null) {
            sr.writeInt32(-1);
            return;
        }

        for (int i = 0; i < enumConsts.length; i++) {
            if(fValue == enumConsts[i]){
                sr.writeInt32(i);
                return;
            }
        }
        sr.writeInt32(-1);
    }
}
