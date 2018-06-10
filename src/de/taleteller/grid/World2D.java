/**
 * www.taleteller.de
 * 
 * TaletellerGrid
 *   World2D
 * 
 * Summary:
 *   
 * 
 * History:
 *   25.11.2017 - Cleaning of code
 *   
 * 
 * Ideas:
 *   
 * 
 * Stephan Hogrefe, Edinburgh, 2017
 */
package de.taleteller.grid;



public abstract class World2D<T_Tiledata, T_Tile extends Tile<T_Tiledata>> {

	/** The array of the gerenic type needs to be created outside of here, 
	 *  and is given to this class via the Initialize() method. */
	protected T_Tile[][] tiles;
	/** Height of the world in number of tiles */
	int height;
	/** Width of the world in number of tiles */
	int width;
	/** The size of one individual tile. Assumes they are all the same size */
	int tile_size;
	
	public World2D(int tilesize) {
		this.tile_size = tilesize;

		/* Note: tiles array is created in extending class! */
	}
	
	public void Initialize(T_Tile[][] tiles) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		/* connect all tiles to create the connected world. */
		connectTiles();
	}
	
	private void connectTiles() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j].connectToNeighbors(tiles);
			}
		}
	}
	
	public T_Tile[][] getTiles() {
		return tiles;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getTile_size() {
		return tile_size;
	}

	public void setTiles(T_Tile[][] tiles) {
		this.tiles = tiles;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setTile_size(int tile_size) {
		this.tile_size = tile_size;
	}
	
	

}
