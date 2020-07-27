package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Tile is an abstract class and the super class of two sub classes:
 * OccupiedTile, and EmptyTile.
 */
public abstract class Tile {

    //Protected variable means it can be accessed only by sub classes of 'Tile';
    //Variables:
    //final Map of empty tiles on board
    protected final int tileCoordinate;
    private final static  Map <Integer , EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
    //The method:
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTilesMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTilesMap.put(i, new EmptyTile(i));
        }      //Method from 'Guava' (A library of google) that returns,
                //could use also: Collections.unmodifiableMap(emptyTileMap);
        return ImmutableMap.copyOf(emptyTilesMap);
    }
    public static Tile createTile (final int tileCoordinate, final Piece piece) {
    return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }
    //Ctor:
    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    //Abstract methods of the superclass:
    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    //The class that represents an empty tile:
    public static final class EmptyTile extends Tile {

        //Ctor
        private EmptyTile(final int coordinate) {
            super(coordinate);
        }
        //@Overriding superclasses methods:
        @Override
        public boolean isTileOccupied() {
            return false;
        }
        @Override
        public Piece getPiece() {
            return null;
        }
    }
    public static final class OccupiedTile extends Tile {

        //Variables
        private final Piece pieceOnTile;

        //Ctor of an Occupied Tile:
        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }
        //@Overriding methods from superclass (Tile):
        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}

