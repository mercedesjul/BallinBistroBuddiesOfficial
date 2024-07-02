package lulian.ballinbistrobuddies.model;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class Position implements PositionInterface{
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public int getX(){
        return x;
    }
    @Override
    public int getY(){
        return y;
    }
    @Override
    public void setX(int x){
        this.x = x;
    }
    @Override
    public void setY(int y){
        this.y = y;
    }

    public boolean equals(@NotNull Position position) {
        return x == position.x && y == position.y;
    }
}
