package dae.math.script;

/**
 *
 * @author Koen.Samyn
 */
public class Reference {

    private String id;
    private int[] indices;

    public Reference(String id) {
        this.id = id;
    }
    
    public Reference(String id, int[] indices){
        this.id = id;
        this.indices = indices;
    }

    public String getId() {
        return id;
    }

    public boolean hasIndices() {
        return indices != null && indices.length > 0;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }
}
