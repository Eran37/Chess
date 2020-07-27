package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static com.chess.engine.board.Move.*;

public class King extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
    King(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffSet : CANDIDATE_MOVE_COORDINATE) {
            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffSet) ||
               isEighthColumnExclusion(this.piecePosition, currentCandidateOffSet)) {
                continue;
            }
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffSet;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
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
        return ImmutableList.copyOf(legalMoves);
    }
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffSet == -9 || candidateOffSet == -1
                || candidateOffSet == 7);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                (candidateOffSet == -7 || candidateOffSet == 1 || candidateOffSet == 9);
    }
}
