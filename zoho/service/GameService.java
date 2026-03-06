package com.zoho.service;

import com.zoho.model.Ball;
import com.zoho.model.Board;
import com.zoho.model.Brick;

public class GameService {

    private Board board;
    private Ball ball;

    public GameService(Board board, Ball ball) {
        this.board = board;
        this.ball = ball;
    }

    public void move(String command) {

        int dRow = -1;
        int dCol = 0;

        if (command.equalsIgnoreCase("Lt"))
            dCol = -1;
        else if (command.equalsIgnoreCase("Rt"))
            dCol = 1;

        int newRow = ball.getRow();
        int newCol = ball.getCol();

        int size = 7;

        while (true) {

            newRow += dRow;
            newCol += dCol;

            // ✅ Stop if top reached
            if (newRow <= 0)
                break;

            // ✅ Wall bounce
            if (newCol <= 0 || newCol >= size - 1) {

                if (dCol == -1)
                    dCol = 1;
                else if (dCol == 1)
                    dCol = -1;

                newCol += dCol * 2;
            }

            Brick brick = board.getBrick(newRow, newCol);

            if (brick != null) {

                brick.hit();

                if (brick.isDestroyed()) {
                    board.removeBrick(newRow, newCol);
                    System.out.println("Brick destroyed at (" + newRow + "," + newCol + ")");
                }

                ball.decreaseLife();
                break;
            }
        }

        ball.resetPosition(6, 3);
    }
}