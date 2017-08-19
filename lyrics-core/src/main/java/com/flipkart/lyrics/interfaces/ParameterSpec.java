package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class ParameterSpec {
    protected ParameterSpec() {
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return Song.factory.createParameterBuilder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return Song.factory.createParameterBuilder(clazz, name, modifiers);
    }

    public Object getParameterSpec() {
        return null;
    }

    public static abstract class Builder {
        public TypeName typeName;
        public String name;
        public Modifier[] modifiers;

        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            this.typeName = typeName;
            this.name = name;
            this.modifiers = modifiers;
        }

        public abstract Builder addAnnotation(Class<?> clazz);

        public abstract Builder addAnnotation(ClassName className);

        public abstract ParameterSpec build();
    }
}
