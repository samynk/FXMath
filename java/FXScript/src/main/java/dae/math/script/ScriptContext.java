package dae.math.script;

import dae.math.script.format.MathFormat;
import dae.math.script.specops.ICalc;
import dae.math.script.values.Double1;
import dae.math.script.values.IArray;
import dae.math.script.values.String1;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * TODO: make rendermath available.
 * TODO: animation timer.
 * @author Koen.Samyn
 */
public class ScriptContext {

    private final HashMap<String, ScriptValue> referenceMap = new HashMap<>();
    //private final HashMap<String, RenderMath> pictureMap = new HashMap<>();
    private final HashMap<String, FunctionScriptContext> functionMap = new HashMap<>();
    private final static HashMap<String, ScriptConstructor> constructors = new HashMap<>();
    private final static HashMap<Class, ScriptMethodCache> methodCache = new HashMap<>();
    private final ArrayList<String> debugVariables = new ArrayList<>();
    

    private final FormulaOptions defaultOptions = new FormulaOptions();
    private final MathFormat format = new MathFormat(new String1("###.#"));

    private long previousTime = -1;

    private boolean dirty = true;
    private Random scriptRandom = new Random(System.currentTimeMillis());

    public static MathFormat debugFormat = new MathFormat(new String1("#.####"));
    private Path documentPath;

    static {
        registerConstructor("camera", "dae.math.render3d.Camera3D");
        registerConstructor("picture2d", "dae.math.render2d.Picture2D");
        registerConstructor("point", "dae.math.render2d.Point");
        registerConstructor("point3d", "dae.math.render3d.Point3D");
        registerConstructor("circle3d", "dae.math.render3d.Circle3D");
        registerConstructor("sphere3d", "dae.math.render3d.Sphere3D");
        registerConstructor("cone3d", "dae.math.render3d.mesh.ConeMesh3D");
        registerConstructor("particle3d", "dae.math.render3d.Particle3D");
        registerConstructor("vector", "dae.math.render2d.Vector");
        registerConstructor("vector3d", "dae.math.render3d.Vector3D");
        registerConstructor("quaternion", "dae.math.render3d.Quaternion3D");
        registerConstructor("triangle", "dae.math.render2d.Triangle2D");
        registerConstructor("triangle3d", "dae.math.render3d.Triangle3D");
        registerConstructor("rect", "dae.math.render2d.Quad2D");
        registerConstructor("quad3d", "dae.math.render3d.Quad3D");
        registerConstructor("box3d", "dae.math.render3d.Box3D");
        registerConstructor("mesh3d", "dae.math.render3d.mesh.Mesh3D");
        registerConstructor("vsegment3d", "dae.math.render3d.VectorSegment3D");
        registerConstructor("projectpoint", "dae.math.render2d.ProjectedPoint");
        registerConstructor("projectvector", "dae.math.render2d.ProjectedVector");
        registerConstructor("angle", "dae.math.render2d.Angle");
        registerConstructor("vangle", "dae.math.render2d.VectorAngle");
        registerConstructor("angle3d", "dae.math.render3d.Angle3D");
        registerConstructor("arc3d", "dae.math.render3d.ArcSegment3D");
        registerConstructor("decimalformat", "dae.math.script.MathFormat");
        registerConstructor("cellformat", "dae.math.script.format.CellFormat");
        registerConstructor("angleformat", "dae.math.script.AngleFormat");
        registerConstructor("piformat", "dae.math.script.PiFormat");
        registerConstructor("transform2d", "dae.math.render2d.Transform2D");
        registerConstructor("segment", "dae.math.render2d.Segment2D");
        registerConstructor("measurement", "dae.math.render2d.Measurement2D");
        registerConstructor("measurement3d", "dae.math.render3d.Measurement3D");
        registerConstructor("measurement3D", "dae.math.render3d.Measurement3D");
        registerConstructor("line", "dae.math.render2d.ParameterLine");
        registerConstructor("circle", "dae.math.render2d.Circle2D");
        registerConstructor("slider", "dae.math.controls.DoubleSlider");
        registerConstructor("button", "dae.math.controls.MathButton");
        registerConstructor("gaussian", "dae.math.render2d.Gaussian2D");
        registerConstructor("arc", "dae.math.render2d.Arc2D");
        registerConstructor("function", "dae.math.render2d.Function2D");
        registerConstructor("polarfunction", "dae.math.render2d.PolarFunction2D");
        registerConstructor("doubleanimator", "dae.math.animation.Double1Animator");
        registerConstructor("panimator", "dae.math.animation.ParticleAnimator");
        registerConstructor("bone", "dae.math.render3d.joints.Bone3D");
        registerConstructor("dof1target", "dae.math.render3d.joints.Dof1Target");
        registerConstructor("dof1alignment", "dae.math.render3d.joints.Dof1Alignment");
        registerConstructor("dof2target", "dae.math.render3d.joints.Dof2Target");
        registerConstructor("quatZXY", "dae.math.render3d.joints.QuaternionZXY");
        registerConstructor("axis3d", "dae.math.render3d.Axis3D");
        registerConstructor("axis", "dae.math.render2d.Axis");
        registerConstructor("matrix44", "dae.math.render3d.Matrix3D");
        registerConstructor("matrix41", "dae.math.render3d.ColumnMatrix3D");
        registerConstructor("matrix33", "dae.math.render3d.Matrix33");
        registerConstructor("matrix", "dae.math.values.Matrix");
        registerConstructor("path2d", "dae.math.render2d.Path2D");
        registerConstructor("raycircleintersect", "dae.math.render2d.intersection.RayCircleIntersection");
        registerConstructor("raycam3d", "dae.math.render3d.RayCamera3D");

        // 2d nodes
        registerConstructor("node", "dae.math.render2d.nodeblock.NodeBlock");
        registerConstructor("gnode", "dae.math.render2d.graph.GraphNode");
        registerConstructor("gconnector", "dae.math.render2d.graph.GraphConnector");
        registerConstructor("connector", "dae.math.render2d.nodeblock.IOConnector");
        registerConstructor("neuralnet", "dae.math.render2d.neuralnet.NeuralNet");
        registerConstructor("mlenv", "dae.ml.Environment");
    }

    public ScriptContext() {
        this(true);
    }

    public ScriptContext(boolean startTimer) {
        defaultOptions.setMathFormat(format);

        Double1 pi = new Double1("π", Math.PI);
        pi.setId("PI");

        Double1 e = new Double1("e", Math.E);
        e.setId("e");

        referenceMap.put("PI", pi);
        referenceMap.put("e", e);
        referenceMap.put("NORTH", new Double1(+Math.PI / 2));
        referenceMap.put("SOUTH", new Double1(-Math.PI / 2));
        referenceMap.put("EAST", new Double1(0));
        referenceMap.put("WEST", new Double1(Math.PI));
    }

    private static void registerConstructor(String name, String className) {
        ScriptConstructor pointConstructor = new ScriptConstructor(name, className);
        constructors.put(name, pointConstructor);
    }

    public void construct(String name, Reference reference, String spaceID, ScriptValue... values) {
        ScriptConstructor sc = constructors.get(name);
        if (sc != null) {
            ScriptVariable ssv = null;
            if (values.length == 0) {
                ssv = sc.construct();
            } else {
                ssv = sc.construct(values);
            }
            if (ssv != null) {
                ssv.setScriptContext(this);
                ssv.setSpaceID(spaceID);
                if (reference.hasIndices()) {
                    ScriptValue value = referenceMap.get(reference.getId());
                    if (value instanceof IArray) {
                        IArray matrix = (IArray) value;
                        matrix.set(reference.getIndices(), (ICalc) ssv);
                    }
                } else {
                    referenceMap.put(reference.getId(), ssv);
                    ssv.setId(reference.getId());
                }
            } else {
                System.out.println("Could not construct : " + name + " with id : " + reference);
                System.out.println("With following parameters : ");
                for (ScriptValue value : values) {
                    if (value != null) {
                        System.out.println("Type: " + value.getClass().getName() + ", " + value.toString());
                    }
                }
            }
        }
    }

    public void addVariable(String name, ScriptVariable value, String space) {
        if (value != null) {
            value.setSpaceID(space);
            value.setCaption(name);
            referenceMap.put(name, value);
            value.setScriptContext(this);
        }
    }

    public void addValue(String name, ScriptValue value, String space) {
        if (value != null) {
            value.setSpaceID(space);

            referenceMap.put(name, value);
            if (name != null && !name.isEmpty()) {
                value.setId(name);
                value.setCaption(name);
            }
        }
    }

    public ScriptValue getVariable(String name) {
        return referenceMap.get(name);
    }

    public static ScriptMethodCache getScriptMethodCache(Class objectClass) {
        ScriptMethodCache cache = methodCache.get(objectClass);
        if (cache == null) {
            cache = new ScriptMethodCache(objectClass);
            methodCache.put(objectClass, cache);
        }
        return cache;
    }

    public void call(String method, String id, ScriptValue[] values) {
        ScriptValue sv = this.getVariable(id);
//        for(int i =0; i < values.length; ++i)
//        {
//            if (values[i] instanceof ICalc){
//                values[i] = (ScriptValue)((ICalc)values[i]).getValue();
//            }
//        }

        if (sv != null) {
            if (!callMethod(values, method, id, sv)) {

                if (sv instanceof ICalc) {
                    sv = (ScriptValue) ((ICalc) sv).getValue();
                    callMethod(values, method, id, sv);
                }
            }
        }
    }

    private boolean callMethod(ScriptValue[] values, String method, String id, ScriptValue sv) {
        Class[] parameterTypes = new Class[values.length];

        for (int i = 0; i < values.length; ++i) {
            if (values[i] != null) {
                parameterTypes[i] = values[i].getClass();
            } else {
                System.out.println("Value with index " + i + " is null for " + method + " of object " + id + ".");
            }
        }
        try {
            // System.out.println("Trying to call " + method);
            Class objectClass = sv.getClass();
            ScriptMethodCache cache = getScriptMethodCache(objectClass);
            Method m = cache.findMethod(method, parameterTypes);
            if (m != null) {
                m.invoke(sv, (Object[]) values);
            } else {
                // System.out.println("Could not find " + method + " of object " + id + ".");
                return false;
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println("Could not call " + method + " of object " + id + ".");
            System.out.println("With types: ");
            for (Class c : parameterTypes) {
                System.out.println("parameter : " + c.getName());
            }
            System.out.println("And values:");
            for (Object o : values) {
                System.out.println("value : " + o);
            }
            Logger.getLogger(ScriptConstructor.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            return false;
        }
        return true;
    }

    public FormulaOptions getDefaultFormulaOptions() {
        return this.defaultOptions;
    }

    public MathFormat getDefaultFormat() {
        return this.format;
    }

    public void repaint() {
        this.dirty = true;
    }

    public double generateRandomDouble(double min, double max) {
        return min + scriptRandom.nextDouble() * (max - min);
    }

    public double truncateDigits(double value, int digits) {
        double factor = Math.pow(10, digits);
        double trunc = Math.floor(value * factor);
        return trunc / factor;
    }

    public void clear() {
        referenceMap.clear();
    }

    public Collection<ScriptValue> getVariables() {
        return debugVariables.stream()
                .map(referenceMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public void addDebugVariable(String id) {
        debugVariables.add(id);
    }

    public void setDocumentPath(Path documentPath) {
        this.documentPath = documentPath;
    }

    public Path getDocumentPath() {
        return documentPath;
    }

    public void setArrayElement(Reference ref, ScriptValue element) {
        ScriptValue sv = this.referenceMap.get(ref.getId());
        if (sv instanceof IArray && element instanceof ICalc) {
            IArray array = (IArray) sv;
            ((IArray) sv).set(ref.getIndices(), (ICalc) element);
        }
    }

    /*
    public void addPicture(String id, RenderMath picture) {
        this.pictureMap.put(id, picture);
    }

    public RenderMath getPicture(String id) {
        return pictureMap.get(id);
    }
    */

    public void addFunction(String functionName, FunctionScriptContext function) {
        functionMap.put(functionName, function);
    }

    public void callFunction(String functionName) {
        if (functionMap.containsKey(functionName)) {
            functionMap.get(functionName).execute();
        }
    }

    public void updateAll() {
        for (var sv : this.referenceMap.values()) {
            if (sv instanceof ICalc var) {
                var.update();
            }
        }
    }
}
