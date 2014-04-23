
public class NameComparator implements Comparator {

	@Override
	public boolean equals(String s1, String s2) {
		return s1.toLowerCase().contains(s2.toLowerCase());
	}

}
