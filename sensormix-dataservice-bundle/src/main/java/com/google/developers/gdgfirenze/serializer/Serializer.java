package com.google.developers.gdgfirenze.serializer;

import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.developers.gdgfirenze.model.AbstractSample;

public class Serializer {
	Kryo k;
	
	public Serializer() {
		k=new Kryo();
		k.setClassLoader(Serializer.class.getClassLoader());
	}
	
	public byte[] serialize(AbstractSample orig) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output buffer = new Output(bos);
        k.writeClassAndObject(buffer, orig);
        return buffer.getBuffer();
    }
	
	public AbstractSample deserialize(byte[] buffer) {
        Input iBuff = new Input(buffer);
        
        return (AbstractSample)k.readClassAndObject(iBuff);
    }
}
