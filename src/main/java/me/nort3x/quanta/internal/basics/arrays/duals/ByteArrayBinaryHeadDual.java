package me.nort3x.quanta.internal.basics.arrays.duals;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;

public class ByteArrayBinaryHeadDual implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o, QuantaConfiguration configuration) throws IllegalAccessException {
        byte[] bs = ds.readByteArray();
        if (bs == null)
            return;
        Byte[] bis = new Byte[bs.length];

        for (int i = 0; i < bis.length; i++) {
            bis[i] = bs[i];
        }
        f.set(o, bis);
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        Byte[] bis = (Byte[]) f.get(o);
        if(bis==null){
            sr.writeByteArray(null);
            return;
        }
        byte[] bs = new byte[bis.length];
        for (int i = 0; i < bs.length; i++) {
            bs[i]= bis[i];
        }
        sr.writeByteArray(bs);
    }
}
