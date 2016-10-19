package frontend;

public class Pen {
    private boolean isDown;
    
    public Pen() {
        isDown = true;
    }
    
    public boolean isDrawing() {
        return isDown;
    }
    
    /**
     * @param penStatus - true sets pen down (draws)
     *                    false raises pen (stops drawing)
     */
    public void setDrawing(boolean penStatus) {
        isDown = penStatus;
    }
}
