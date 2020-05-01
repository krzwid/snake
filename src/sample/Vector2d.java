package sample;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals (Object other) {
        if(this == other) return true;
        if(!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
