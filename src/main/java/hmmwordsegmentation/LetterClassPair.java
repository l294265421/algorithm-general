package hmmwordsegmentation;

/**
 * 字符/类别对 比如 你/S
 * @author yuncong
 *
 */
public class LetterClassPair {
	private String letter;
	private String classRepresentation;
	public LetterClassPair(String letter, String classRepresentation) {
		super();
		this.letter = letter;
		this.classRepresentation = classRepresentation;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getClassRepresentation() {
		return classRepresentation;
	}
	public void setClassRepresentation(String classRepresentation) {
		this.classRepresentation = classRepresentation;
	}
	@Override
	public String toString() {
		return "LetterClassPair [letter=" + letter + ", classRepresentation="
				+ classRepresentation + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((classRepresentation == null) ? 0 : classRepresentation
						.hashCode());
		result = prime * result + ((letter == null) ? 0 : letter.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LetterClassPair other = (LetterClassPair) obj;
		if (classRepresentation == null) {
			if (other.classRepresentation != null)
				return false;
		} else if (!classRepresentation.equals(other.classRepresentation))
			return false;
		if (letter == null) {
			if (other.letter != null)
				return false;
		} else if (!letter.equals(other.letter))
			return false;
		return true;
	}
}
