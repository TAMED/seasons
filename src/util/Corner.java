package util;

public enum Corner {
	TOPLEFT  {public Corner opposite() { return BOTTOMRIGHT; }},
	TOPRIGHT {public Corner opposite() { return BOTTOMLEFT; }},
	BOTTOMLEFT    {public Corner opposite() { return TOPRIGHT; }},
	BOTTOMRIGHT  {public Corner opposite() { return TOPLEFT; }};
	public abstract Corner opposite();
}
