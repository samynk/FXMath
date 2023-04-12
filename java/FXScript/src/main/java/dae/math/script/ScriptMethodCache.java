/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.math.script;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Koen.Samyn
 */
public class ScriptMethodCache {

    Class objectClass;
    // all the methods with the same name.
    private HashMap<String, Method[]> methodMap = new HashMap<>();

    public ScriptMethodCache(Class objectClass) {
        this.objectClass = objectClass;
    }

    public Method findMethod(String methodName, Class[] parameterTypes) {
        Method[] methods = methodMap.get(methodName);
        if (methods == null) {
            ArrayList<Method> collector = new ArrayList<>();
            Method[] allMethods = objectClass.getMethods();
            for (Method m : allMethods) {
                if (m.getName().equals(methodName)) {
                    collector.add(m);
                }
            }
            methods = new Method[collector.size()];
            collector.toArray(methods);
            methodMap.put(methodName, methods);
        }
        for (int i = 0; i < methods.length; ++i) {
            Method current = methods[i];
            if (current.getParameterCount() == parameterTypes.length) {
                Class[] types =  current.getParameterTypes();
                boolean found = true;
//                System.out.println("length of types : " + types.length);
//                System.out.println("length of parameterTypes : " + parameterTypes.length);
                for(int j = 0; j < types.length; ++j)
                {
                    if ( parameterTypes[j] == null ){
                        return null;
                    }
                    if ( !types[j].equals(parameterTypes[j]) && !types[j].isAssignableFrom(parameterTypes[j])){
                        found = false;
                        break;
                    }
                }
                if (found){
                    return current;
                }
            }
        }
        return null;
    }
}
