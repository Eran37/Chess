package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.BoardUtils;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVES_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
    //Ctor
    public Knight(int piecePosition, Alliance pieceAlliance) {
        //to the Ctor of super (Piece)
        super(piecePosition, pieceAlliance);
    }
    //Method from super
    @Override                           // Method gets a board in a current position in the game.
    public Collection<Move> calculateLegalMoves(final Board board) {
        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVES_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)
                || isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)
                || isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset)
                || isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board,this, candidateDestinationCoordinate));
                } else {
                  final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                  final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                  if (this.pieceAlliance != pieceAlliance){
                      legalMoves.add(new AttackMove(
                           board, this, candidateDestinationCoordinate, pieceAtDestination));
                  }
                }
            }
        }
                //Method of guava (library if google);
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffSet == -17 || candidateOffSet == -10
                || candidateOffSet == 6 || candidateOffSet == 15);
    }
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffSet == -10 || candidateOffSet == 6);
    }
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffSet == -6 || candidateOffSet == 10);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == -15 || candidateOffSet == -6
                || candidateOffSet == 10 || candidateOffSet == 17);
    }
}
