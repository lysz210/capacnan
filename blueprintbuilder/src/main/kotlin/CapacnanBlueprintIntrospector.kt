package it.lysz210.akasha.capacnan.scriptbuilder

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
import java.lang.reflect.Modifier

class CapacnanBlueprintIntrospector : JacksonAnnotationIntrospector() {
    override fun findNameForSerialization(a: com.fasterxml.jackson.databind.introspect.Annotated): com.fasterxml.jackson.databind.PropertyName? {
        if (a is AnnotatedMethod) {
            val member = a.annotated

            // If it's a public interface/class method with 0 args, treat its name as the JSON property name
            if (Modifier.isPublic(member.modifiers) &&
                member.parameterCount == 0 &&
                member.returnType != Void.TYPE &&
                !member.name.startsWith("get") &&
                !member.name.startsWith("is") &&
                member.name != "toString" &&
                member.name != "hashCode") {
                return com.fasterxml.jackson.databind.PropertyName.construct(member.name)
            }
        }
        return super.findNameForSerialization(a)
    }
}