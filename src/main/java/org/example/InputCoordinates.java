package org.example;

import org.example.board.Board;
import org.example.board.BoardFactory;
import org.example.board.Move;
import org.example.piece.King;
import org.example.piece.Piece;

import java.util.Scanner;
import java.util.Set;

public class InputCoordinates {
    private static final Scanner scanner = new Scanner(System.in);

    public static Coordinates input(){
        while(true){
            System.out.println("Please, enter coordinates (example a1)");
            String line = scanner.nextLine();

            if( line.length()!= 2) {
                System.out.println("Invalid format");
                continue;
            }

            char fileChar = line.charAt(0);
            char rankChar = line.charAt(1);

            if (!Character.isLetter(fileChar)){
                System.out.println("Invalid format");
                continue;
            }

            if (!Character.isDigit(rankChar)){
                System.out.println("Invalid format");
                continue;
            }

            int rank = Character.getNumericValue(rankChar);
            if (rank < 1 || rank > 8){
                System.out.println("Invalid format");
                continue;
            }

            File file = File.fromChar(fileChar);
            if (file == null){
                System.out.println("Invalid format");
                continue;
            }

            return new Coordinates(file, rank);
        }
    }

    public static Coordinates inputPieceCoordinatesForColor(Color color, Board board){
        while (true){
            System.out.println("Enter coordinates for a piece to move");
            Coordinates coordinates = input();
            if (board.isSquareEmpty(coordinates)){
                System.out.println("Empty square");
                continue;
            }
            Piece piece = board.getPiece(coordinates);
            if(piece.color!= color){
                System.out.println("Wrong color");
                continue;
            }

            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);
            if (availableMoveSquares.size() == 0){
                System.out.println("Blocked piece");
                continue;
            }
            return coordinates;
        }

    }

    public static Coordinates inputAvailableSquare(Set<Coordinates> coordinates){
        while(true){
            System.out.println("Enter move for selected piece");
            Coordinates input = input();

            if (!coordinates.contains(input)){
                System.out.println("Non-available square");

            continue;
        }
        return input;
        }

    }


    public static Move inputMove(Board board, Color color, BoardConsoleRenderer renderer){

        while (true) {
            //input
            Coordinates sourceCoordinates = InputCoordinates.inputPieceCoordinatesForColor(
                    color, board);
            Piece piece = board.getPiece(sourceCoordinates);
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            renderer.render(board, piece);
            Coordinates targetCoordinates = InputCoordinates.inputAvailableSquare(availableMoveSquares);

            Move move = new Move(sourceCoordinates, targetCoordinates);
            if (validateIfKingInCheckAfterMove(board, color, move)) {
                System.out.println("your king is under attack");
                continue;
            }
            return move;
        }

    }

    private static boolean validateIfKingInCheckAfterMove(Board board, Color color, Move move) {
        Board copy = new BoardFactory().copy(board);
        copy.makeMove(move);
        // assumed that king is on the board
        Piece king = copy.getPieceByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();
        return copy.isSquareAttackedByColor(king.coordinates, color.opposite());

    }
}
