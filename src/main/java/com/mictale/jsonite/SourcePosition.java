package com.mictale.jsonite;

/**
 * A description of a the position in a source file.
 */
public final class SourcePosition {

    /**
     * Unspecified source location.
     */
	public static final SourcePosition UNSPECIFIED = new SourcePosition(-1, -1, -1);
	
	private final int position;
	
	private final int line;
	
	private final int column;
	
	public SourcePosition(int position, int line, int column) {
		this.position = position;
		this.line = line;
		this.column = column;
	}

	/**
	 * The character position that this object references.
	 * @return the character position.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * The line position.
	 * 
	 * The one-based line number of this position.
	 * @return the line number.
	 */
	public int getLine() {
		return line;
	}
	
	/**
	 * The character position in the line.
	 * 
	 * @see #getLine()
	 * @return the character position.
	 */
	public int getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return "(" + line + ", " + column + ")";
	}
}
