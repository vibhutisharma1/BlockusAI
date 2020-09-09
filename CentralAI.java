import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class CentralAI extends Player
{
    private boolean start = true;

    public CentralAI(int color,String name)
    {
        super(color, name);
        start = true;
    }

    //recode to choose based on position first
    public Move getMove(BlokusBoard board)
    {
        start = true;

        ArrayList<IntPoint> avaiableMoves = board.moveLocations(getColor());
        //Collections.shuffle(avaiableMoves);
        //System.out.println("available move locations "+avaiableMoves);
        ArrayList<Integer> usableFives = new ArrayList<>();
        ArrayList<Integer> usableFours = new ArrayList<>();
        ArrayList<Integer> usableThrees = new ArrayList<>();
        ArrayList<Integer> usableTwos = new ArrayList<>();
        ArrayList<Integer> usableOnes = new ArrayList<>();
        boolean[] used = (getColor()==BlokusBoard.ORANGE)?board.getOrangeUsedShapes():board.getPurpleUsedShapes();
        for(int x=0; x<used.length; x++)
            if(!used[x])
            {
                if(board.getShapes().get(x).getSquareCount()==1)
                    usableOnes.add(x);
                else if(board.getShapes().get(x).getSquareCount()==2)
                    usableTwos.add(x);
                else if(board.getShapes().get(x).getSquareCount()==3)
                    usableThrees.add(x);
                else if(board.getShapes().get(x).getSquareCount()==4)
                    usableFours.add(x);
                else
                    usableFives.add(x);
            }

        Collections.shuffle(usableFives);
        Collections.shuffle(usableFours);
        Collections.shuffle(usableThrees);
        Collections.shuffle(usableTwos);
        Collections.shuffle(usableOnes);

        Move fiveMove = moveFromList(board,avaiableMoves,usableFives);
        if(fiveMove!=null) {

            return fiveMove;
        }
        Move fourMove = moveFromList(board,avaiableMoves,usableFours);
        if(fourMove!=null){

            return fourMove;
        }

        Move threeMove = moveFromList(board,avaiableMoves,usableThrees);
        if(threeMove!=null)
        {

            return threeMove;
        }

        Move twoMove = moveFromList(board,avaiableMoves,usableTwos);
        if(twoMove!=null)
        {
            return twoMove;
        }

        Move oneMove = moveFromList(board,avaiableMoves,usableOnes);
        if(oneMove!=null)
        {

            return oneMove;
        }

        return null;
    }

    private Move moveFromList(BlokusBoard board,ArrayList<IntPoint> avaialbeMoves, ArrayList<Integer> shapeLocs) {
        ArrayList<IntPoint> moves = (ArrayList<IntPoint>) (board.moveLocations(getColor()).clone());

        if (moves.size() > 1 && getColor() == board.ORANGE) {
            ArrayList<IntPoint> moveRight = avaialbeMoves;
            IntPoint justPlaced = board.purpleLastMove.getPoint();
            //this code.. ignore for now... it tries to place block but doesnt really
            /*for (int i = 0; i < avaialbeMoves.size()-1; i++) {
                for (int j = i + 1; j < avaialbeMoves.size(); j++) {
                    IntPoint close;
                    IntPoint far;
                    if( Math.abs(avaialbeMoves.get(i).getX()-justPlaced.getX()) > Math.abs(avaialbeMoves.get(j).getX()-justPlaced.getX()) &&
                            Math.abs(avaialbeMoves.get(i).getY()-justPlaced.getY()) > Math.abs(avaialbeMoves.get(j).getY()-justPlaced.getY()) ) {
                        System.out.println("here");
                        //System.out.println(Math.abs(avaialbeMoves.get(i).getX()-justPlaced.getX()) + " " + Math.abs(avaialbeMoves.get(j).getX()-justPlaced.getX()) + " " +
                        //Math.abs(avaialbeMoves.get(i).getY()-justPlaced.getY()) + " " + Math.abs(avaialbeMoves.get(j).getY()-justPlaced.getY()));
                        close = avaialbeMoves.get(j);
                        far = avaialbeMoves.get(i);
                        moveRight.set(i, close);
                        moveRight.set(j, far);
                    }
                }
            }*/


            //moves out towards the opponents area
            for (int i = 0; i < avaialbeMoves.size() - 1; i++) {
                for (int j = i + 1; j < avaialbeMoves.size(); j++) {
                    IntPoint big;
                    IntPoint small;
                    if (avaialbeMoves.get(i).getX() < avaialbeMoves.get(j).getX()) {
                        big = avaialbeMoves.get(j);
                        small = avaialbeMoves.get(i);
                        moveRight.set(i, big);
                        moveRight.set(j, small);
                    } else if (avaialbeMoves.get(i).getX() == avaialbeMoves.get(j).getX()) {
                        if (avaialbeMoves.get(i).getY() < avaialbeMoves.get(j).getY()) {
                            big = avaialbeMoves.get(j);
                            small = avaialbeMoves.get(i);
                        } else {
                            small = avaialbeMoves.get(j);
                            big = avaialbeMoves.get(i);
                        }
                        moveRight.set(i, big);
                        moveRight.set(j, small);
                    }
                }
            }
            moves = moveRight;

        } else if (moves.size() > 1 && getColor() == board.PURPLE) {
            ArrayList<IntPoint> moveLeft = avaialbeMoves;
            IntPoint justPlaced = board.orangeLastMove.getPoint();
            for (int i = 0; i < avaialbeMoves.size() - 1; i++) {
                for (int j = i + 1; j < avaialbeMoves.size(); j++) {
                    /*IntPoint close;
                    IntPoint far;
                    if( Math.abs(avaialbeMoves.get(i).getX()-justPlaced.getX()) > Math.abs(avaialbeMoves.get(j).getX()-justPlaced.getX()) &&
                            Math.abs(avaialbeMoves.get(i).getY()-justPlaced.getY()) > Math.abs(avaialbeMoves.get(j).getY()-justPlaced.getY()) ) {
                        System.out.println("here");
                        //System.out.println(Math.abs(avaialbeMoves.get(i).getX()-justPlaced.getX()) + " " + Math.abs(avaialbeMoves.get(j).getX()-justPlaced.getX()) + " " +
                        //Math.abs(avaialbeMoves.get(i).getY()-justPlaced.getY()) + " " + Math.abs(avaialbeMoves.get(j).getY()-justPlaced.getY()));
                        close = avaialbeMoves.get(j);
                        far = avaialbeMoves.get(i);
                        moveLeft.set(i, close);
                        moveLeft.set(j, far);
                    }
                }*/

                    IntPoint big;
                    IntPoint small;
                    if (avaialbeMoves.get(i).getX() > avaialbeMoves.get(j).getX()) {
                        big = avaialbeMoves.get(j);
                        small = avaialbeMoves.get(i);
                        moveLeft.set(i, big);
                        moveLeft.set(j, small);
                    } else if (avaialbeMoves.get(i).getX() == avaialbeMoves.get(j).getX()) {
                        if (avaialbeMoves.get(i).getY() < avaialbeMoves.get(j).getY()) {
                            big = avaialbeMoves.get(j);
                            small = avaialbeMoves.get(i);
                        } else {
                            small = avaialbeMoves.get(j);
                            big = avaialbeMoves.get(i);
                        }
                        moveLeft.set(j, small);
                        moveLeft.set(i, big);
                    }
                }
            }
            moves = moveLeft;
        }


        //testing
        /*for( int j = 0; j < moves.size(); j++ ) {
            System.out.println("movLoc: " + moves.get(j) );
        }*/

        /*for( int i = 0; i < shapeLocs.size(); i++ ) {
            Integer position = shapeLocs.get(i);
            for (int x = 0; x < moves.size(); x++) {
                IntPoint movLoc = moves.get(x);
                if( !start ) {
                    System.out.println(start);
                    if( getColor() == 4  ) {
                        boolean[][] otherShape = board.getShapes().get(board.purpleLastMove.getPieceNumber()).manipulatedShape(board.purpleLastMove.isFlip(), board.purpleLastMove.getRotation());
                        IntPoint otherTopLeft = new IntPoint(board.purpleLastMove.getPoint().getX() , board.purpleLastMove.getPoint().getY());
                        for (int i2 = 0; i2 < 8; i2++) {
                            boolean flip = i2 >= 3;
                            int rotation = i2 % 4;
                            boolean[][] shape = board.getShapes().get(position).manipulatedShape(flip, rotation);
                            for (int r = -shape.length + 1; r < shape.length; r++)
                                for (int c = -shape[0].length + 1; c < shape[0].length; c++) {
                                    IntPoint topLeft = new IntPoint(movLoc.getX() + c, movLoc.getY() + r);
                                    Move test = new Move(position, flip, rotation, topLeft);
                                    if (board.isValidMove(test, getColor())) {
                                        //if (topLeft.getX() + shape[0].length >= otherTopLeft.getX() + otherShape[0].length ) {
                                            return test;
                                        //}
                                        //else {
                                           //continue;
                                       // }
                                    }
                                }
                        }
                    }

                }
                else {
                    if( getColor() == 4 ) {
                        start = false;
                        return new Move( 16, false, 0, new IntPoint(4,4));
                    }
                    else {
                        start = false;
                        return new Move( 16, false, 0, new IntPoint(7,7));
                    }
                }
            }
        }*/

        for (int i1 = 0; i1 < shapeLocs.size(); i1++) {
            Integer p = shapeLocs.get(i1);
            for (int x1 = 0; x1 < moves.size(); x1++) {
                IntPoint movLoc1 = moves.get(x1);
                for (int i3 = 0; i3 < 8; i3++) {
                    boolean flip1 = i3 >= 3;
                    int rotation1 = i3 % 4;
                    boolean[][] shape1 = board.getShapes().get(p).manipulatedShape(flip1, rotation1);
                    for (int r1 = -shape1.length + 1; r1 < shape1.length; r1++)
                        for (int c1 = -shape1[0].length + 1; c1 < shape1[0].length; c1++) {
                            IntPoint topLeft1 = new IntPoint(movLoc1.getX() + c1, movLoc1.getY() + r1);
                            Move test1 = new Move(p, flip1, rotation1, topLeft1);
                            if (board.isValidMove(test1, getColor())) {
                                System.out.println("test " + movLoc1);
                                return test1;
                            }
                        }
                }
            }
        }


        //get opposing most recent piece put down....

        return null;
    }

    /**
     * Returns a clone of the player
     * @return a clone of this player
     */

    public Player freshCopy()
    {
        return new BigMoverAI(getColor(),getName());
    }
}

