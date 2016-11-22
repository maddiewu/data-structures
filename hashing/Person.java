public class Person {

	private String myName;

	public Person(String name) {
		this.myName = name;
	}

	// return a String representation of the Person object
	public String toString() {
		return myName;
	}

	// Change the name of the person
	public void changeName(String newName) {
		this.myName = newName;
	}
	
	// TODO add additional methods
	public int hashCode() {
		return myName.hashCode();
	}

	public boolean equals(Person x) {
		if (this.equals(x)) {
			if (this.hashCode() == x.hashCode()) {
				return true;
			}
		}
		return false;
	}
	
}
