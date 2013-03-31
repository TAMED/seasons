/**
 * 
 */
package util;

/**
 * @author Mullings
 *
 */
public enum Direction {
	LEFT  {public Direction opposite() { return RIGHT; }},
	RIGHT {public Direction opposite() { return LEFT; }},
	UP    {public Direction opposite() { return DOWN; }},
	DOWN  {public Direction opposite() { return UP; }};
	public abstract Direction opposite();
}
