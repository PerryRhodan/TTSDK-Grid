# TTSDK-Grid

This library contains a generic definition of a 2D grid as well as an implementation for a hex grid specifically. The following example will use the hex grid to show how to make use of it.

What needs to be done first is to come up with a class representing tile data, ie the your specifics of what each tile in your world contains. In this example I will assume this is merely an infrastucture value for now.

```java
public class TestTileData {
	// infrastructure
	double infrastructure;
	// ...
}
```

Then you can implement your specific version of the World2D. It uses generic tiles, which in this case will be Hextiles using the above implemented tile data.

```java
public class Testworld
extends World2D<TestTileData, Hextile<TestTileData>> {
	// ...
	public Testworld(int tilesize) {
		super(tilesize);

		// ...
	}
	// ...
}

```

There is one more step to be done before the world is fully set up. The two dimensional tiles array of the world needs to be created and given to the world via the Initialize(...) method.

```java
// create your world. You must give it
// the size of one tile in pixels
Testworld world = new Testworld(64);
		
// create your tiles
Hextile<TestTileData>[][] alltiles = loadTiles(path_to_world_file);
		
// Initialize the world
world.Initialize(alltiles);
```

Your tile data can be accessed from a hextile via the getData() method.

```java
world.getTiles()[0][0].getData().infrastructure = 100;
```

Your world is now set up and there is nothing more for you to be done in this regard. Using TTSDK-Canvas you could now also draw the hexgrid with a few lines of code.

The grid world comes with a a* pathfinding algorithm. Let's say you want to find the quickest path from tile A to tile B considering each tile's infrastructure value. For this you would first implement your path finding options, which could look like this:

```java
PathFinder_Options<TestTileData> pf_options = new PathFinder_Options<TestTileData>() {
  int max_total_cost = 50000;
  @Override
  public int getEstimation(Tile<TestTileData> current, Tile<TestTileData> target) {
  	// a* needs a heuristic estimation. 
  	// We can just use the eucledian distance  for this.
  	return (int) current.getEucledianDistanceTo(target);
  }
  @Override
  public int getCost(Tile<TestTileData> n1, Tile<TestTileData> n2) {
  	// Here we define what makes up the cost moving from 
  	// one tile to the next. In this case we want make
  	// tiles with high infrastructure to be less costly
  	return (int) (1000 - n1.getData().infrastructure - n2.getData().infrastructure);
  }
  @Override
  public int getMaxTotalCost() {
  	// If the cost of the shortest path exceeds this, no path
  	// can be found. Above we set it to 50000.
  	return max_total_cost;
  }
  @Override
  public void setMaxTotalCost(int amount) {
  	// Update the max allowed cost, ie changing the "range"
  	// of the pathfinder
  	max_total_cost = amount;
  }
};
```

Using these options, now the quickest path can calculated by merely calling:

```java
List<Tile<TestTileData>> quickes_route
		= world.getTiles()[0][0].calculatePathTo(world.getTiles()[10][10], pf_options);
// ...
```

This way different paths can be found using different options, for example based on tarrain, infrastructure, unit skills, etc ...
