import java.util.ArrayList;
import java.util.Arrays;

public class AI extends Player
{
    private boolean start;
    BlokusBoard b=new BlokusBoard();

    public AI(int color,String name)
    {
        super(color, name);
        start = true;

    }

    /**
     * Returns a valid move.
     * @param board - the board that a move should be made on
     * @return a valid move, null if non can be found
     */
    public Move getMove(BlokusBoard board)
    {
        int score = 0;
        int best = -1;
        Move bestMove = null;
        //System.out.println("my color is "+getColor() + " the turn is "+board.getTurn());
        ArrayList<IntPoint> avaiableMoves = board.moveLocations(getColor());

        //Collections.shuffle(avaiableMoves);
        //System.out.println("available move locations "+avaiableMoves);
        ArrayList<Integer> usableShapePositions = new ArrayList<>();

        boolean[] used = (getColor()==BlokusBoard.ORANGE)?board.getOrangeUsedShapes():board.getPurpleUsedShapes();
        for(int x=0; x<used.length; x++)
            if(!used[x])
                usableShapePositions.add(x);
        //System.out.println("usable pieces "+ Arrays.toString(used));
        //Collections.shuffle(usableShapePositions);
        if(usableShapePositions.isEmpty() ||avaiableMoves.isEmpty())
            return null;

        else {
            for (int a = 0; a < avaiableMoves.size(); a++) {
                IntPoint movLoc = avaiableMoves.get(a);
                for (int x = usableShapePositions.size() - 1; x > -1; x--) {
                    Integer position = usableShapePositions.get(x);
                    for (int i = 0; i < 8; i++) {
                        boolean flip = i > 3;
                        int rotation = i % 4;
                        boolean[][] shape = board.getShapes().get(position).manipulatedShape(flip, rotation);
                        if (shape.length > 1 && shape[0].length > 1) {
                            for (int r = -shape.length + 1; r < shape.length; r++) {
                                for (int c = -shape[0].length + 1; c < shape[0].length; c++) {
                                    IntPoint topLeft = new IntPoint(movLoc.getX() + c, movLoc.getY() + r);
                                    Move test = new Move(position, flip, rotation, topLeft);
                                    if (board.isValidMove(test, getColor())) {
                                        score = 0;
                                        if ((getColor() == BlokusBoard.ORANGE) ? board.getPurpleSkipped() : board.getOrangeSkipped()) { //blocked
                                            score += board.getShapes().get(position).getSquareCount();
                                            score += ((getColor() == BlokusBoard.ORANGE) ? board.getOrangeMoveLocations().size() : (board.getPurpleMoveLocations().size())) * 2;//score+= available pts after placing
                                            if (score > best) {
                                                bestMove = test;
                                                best = score;
                                            }
                                        }
                                        //create zoning/corners...
                                        else {
                                            int enemy = (getColor() == BlokusBoard.ORANGE) ? board.getPurpleMoveLocations().size() : board.getOrangeMoveLocations().size();
                                            ArrayList<IntPoint> enemyMove = (getColor() == BlokusBoard.ORANGE) ? board.getPurpleMoveLocations() : board.getOrangeMoveLocations();
                                            board.makeMove(test, getColor());
                                            board.changeTurns();
                                            /*(for( int e = 0; e < enemyMove.size(); e++ ) {
                                                int xPos = enemyMove.get(e).getX();
                                                int yPos = enemyMove.get(e).getY();
                                                if( xPos == 0 ) {
                                                    if( board.getBoard()[xPos+1][yPos+1]) {

                                                    }
                                                }
                                            }*/
                                            //score += ((getColor() == BlokusBoard.ORANGE) ? board.getOrangeMoveLocations().size() : (board.getPurpleMoveLocations().size()));
                                            //score += board.getShapes().get(position).getSquareCount();
                                            score = (enemy - ((getColor() == BlokusBoard.ORANGE) ? board.getPurpleMoveLocations().size() : board.getOrangeMoveLocations().size()));
                                            //score += ((getColor() == BlokusBoard.ORANGE) ? board.getOrangeMoveLocations().size() : (board.getPurpleMoveLocations().size()));
                                            //score+= how many moves of enemy it cuts off
                                                /*if( board.getPurpleLastMove() != null ) {
                                                    if( board.getPurpleLastMove().getPoint().getX() >= 8) {
                                                        if (test.getPoint().getX() > board.getPurpleLastMove().getPoint().getX()) {
                                                            score += score;
                                                        }
                                                        if( test.getPoint().getX() >= 5) {
                                                            score+= score/2;
                                                        }
                                                        if( board.getPurpleLastMove().getPoint().getY() >= 6 ) {
                                                            if (test.getPoint().getY() > board.getPurpleLastMove().getPoint().getY()) {
                                                                score = score* 2;
                                                            }
                                                            if( test.getPoint().getY() <= 6 ) {
                                                                score+= score/2;
                                                            }
                                                        }
                                                        if( board.getPurpleLastMove().getPoint().getY() <= 6 ) {
                                                            if (test.getPoint().getY() > board.getPurpleLastMove().getPoint().getY()) {
                                                                score = score* 2;
                                                            }
                                                            if( test.getPoint().getY() >= 6 ) {
                                                                score+= score/2;
                                                            }
                                                        }

                                                    }

                                                    else {
                                                        if (test.getPoint().getX() < board.getPurpleLastMove().getPoint().getX()) {
                                                            score += score;
                                                        }
                                                        if( test.getPoint().getX() < 9) {
                                                            score+= score/2;
                                                        }
                                                        if( board.getPurpleLastMove().getPoint().getY() > 6 ) {
                                                            if (test.getPoint().getY() > board.getPurpleLastMove().getPoint().getY()) {
                                                                score = score* 2;
                                                            }
                                                            if( test.getPoint().getY() < 6 ) {
                                                                score+= score/2;
                                                            }
                                                        }
                                                        if( board.getPurpleLastMove().getPoint().getY() < 6 ) {
                                                            if (test.getPoint().getY() > board.getPurpleLastMove().getPoint().getY()) {
                                                                score = score* 2;
                                                            }
                                                            if( test.getPoint().getY() > 6 ) {
                                                                score+= score/2;
                                                            }
                                                        }
                                                    }
                                                }*/

                                            if (getColor() == BlokusBoard.PURPLE) {
                                                if (board.getOrangeLastMove() != null) {
                                                    if (board.getOrangeLastMove().getPoint().getX() >= 4) {
                                                        if (test.getPoint().getX() > board.getOrangeLastMove().getPoint().getX()) {
                                                            score += score;
                                                        }
                                                        if (test.getPoint().getX() >= 5) {
                                                            score += score / 2;
                                                        }
                                                        if (board.getOrangeLastMove().getPoint().getY() >= 6) {
                                                            if (test.getPoint().getY() > board.getOrangeLastMove().getPoint().getY()) {
                                                                score += score / 2;
                                                            }
                                                            if (test.getPoint().getY() <= 6) {
                                                                score = score * 2;
                                                            }
                                                        }
                                                        if (board.getOrangeLastMove().getPoint().getY() <= 6) {
                                                            if (test.getPoint().getY() > board.getOrangeLastMove().getPoint().getY()) {
                                                                score += score / 2;
                                                            }
                                                            if (test.getPoint().getY() >= 6) {
                                                                score = score * 2;
                                                            }
                                                        }

                                                    }
                                                    else {
                                                        if (test.getPoint().getX() < board.getOrangeLastMove().getPoint().getX()) {
                                                            score += score;
                                                        }
                                                        if (test.getPoint().getX() < 4) {
                                                            score += score / 2;
                                                        }
                                                        if (board.getOrangeLastMove().getPoint().getY() > 6) {
                                                            if (test.getPoint().getY() > board.getOrangeLastMove().getPoint().getY()) {
                                                                score += score / 2;
                                                            }
                                                            if (test.getPoint().getY() < 6) {
                                                                score = score * 2;
                                                            }
                                                        }
                                                        if (board.getOrangeLastMove().getPoint().getY() < 6) {
                                                            if (test.getPoint().getY() > board.getOrangeLastMove().getPoint().getY()) {
                                                                score += score / 2;
                                                            }
                                                            if (test.getPoint().getY() > 6) {
                                                                score = score * 2;
                                                            }
                                                        }
                                                    }
                                                }


                                                }

                                                board.undoMovePiece(test, getColor());
                                                if (score > best) {
                                                    bestMove = test;
                                                    best = score;
                                                }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //sometimes won't work....

            }
            if (bestMove == null && avaiableMoves.size() > 0) {
                System.out.println(avaiableMoves.get(0));
                if (board.isValidMove(new Move(0, false, 0, new IntPoint(avaiableMoves.get(0).getX(), avaiableMoves.get(0).getY())), getColor()))
                    return new Move(0, false, 0, new IntPoint(avaiableMoves.get(0).getX(), avaiableMoves.get(0).getY()));
            }
            score = 0;
            best = -100;
        }

        return bestMove;
    }
   /* public Move nextMove( BlokusBoard board, Move b ) {
        ArrayList<IntPoint> avaiableMoves = board.moveLocations(getColor());
        //Collections.shuffle(avaiableMoves);
        //System.out.println("available move locations "+avaiableMoves);
        ArrayList<Integer> usableShapePositions = new ArrayList<>();

        boolean[] used = (getColor()==BlokusBoard.ORANGE)?board.getOrangeUsedShapes():board.getPurpleUsedShapes();
        for(int x=0; x<used.length; x++)
            if(!used[x])
                usableShapePositions.add(x);
        //System.out.println("usable pieces "+ Arrays.toString(used));
        //Collections.shuffle(usableShapePositions);
        for( int a = 0; a < avaiableMoves.size(); a++ ) {
            IntPoint movLoc = avaiableMoves.get(a);
            for (int x = usableShapePositions.size() - 1; x > -1; x--) {
                Integer position = usableShapePositions.get(x);
                for (int i = 0; i < 8; i++) {
                    boolean flip = i > 3;
                    int rotation = i % 4;
                    boolean[][] shape = board.getShapes().get(position).manipulatedShape(flip, rotation);
                    if (shape.length > 1 && shape[0].length > 1) {
                        for (int r = -shape.length + 1; r < shape.length; r++) {
                            for (int c = -shape[0].length + 1; c < shape[0].length; c++) {
                                IntPoint topLeft = new IntPoint(movLoc.getX() + c, movLoc.getY() + r);
                                Move test = new Move(position, flip, rotation, topLeft);
                                if (board.isValidMove(test, getColor())) {
                                    int enemy = (getColor() == BlokusBoard.ORANGE) ? board.getPurpleMoveLocations().size() : board.getOrangeMoveLocations().size();
                                    board.makeMove(test, getColor());
                                    board.changeTurns();
                                    score += (enemy - ((getColor() == BlokusBoard.ORANGE) ? board.getPurpleMoveLocations().size() : board.getOrangeMoveLocations().size())); //score+= how many moves of enemy it cuts off
                                    board.undoMovePiece(test, getColor());
                                    if (score > best) {
                                        bestMove = test;
                                        best = score;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;

    }*/



    /**
     * Returns a clone of the player
     * @return a clone of this player
     */
    public Player freshCopy()
    {
        return new AI(getColor(),getName());
    }
}

