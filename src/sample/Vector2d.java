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

    public void move(Direction direction, Vector2d maximum){
        switch (direction){
            case NORTH:
                y--;
                if(y < 0) y = maximum.y;
                break;
            case SOUTH:
                y++;
                if(y>maximum.y) y=0;
                break;
            case EAST:
                x++;
                if(x > maximum.x) x = 0;
                break;
            case WEST:
                x--;
                if(x < 0) x = maximum.x;
                break;
            default:
                break;
        }
    }

}
