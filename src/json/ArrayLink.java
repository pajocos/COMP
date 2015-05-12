package json;

public class ArrayLink {
	private int source, target, value,beginColumn, beginLine;

	public void setValues(int source, int target, int value, int beginLine, int beginColumn) {
		this.source = source;
		this.target = target;
		this.value = value;
		this.beginLine = beginLine;
		this.beginColumn = beginColumn;
	}

	public int getSource() {
		return source;
	}
	
	public int getBeginColumn() {
		return beginColumn;
	}

	public int getBeginLine() {
		return beginLine;
	}

	public int getTarget() {
		return target;
	}

	public int getValue() {
		return value;
	}
}
