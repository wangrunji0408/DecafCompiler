package decaf.utils;

public class Complex {
	private int real, image;

	public Complex(int real, int image) {
		this.real = real;
		this.image = image;
	}

	public Complex(int real) {
		this.real = real;
		this.image = 0;
	}

	public Complex() {
	}

	public int getReal() {
		return real;
	}

	public void setReal(int real) {
		this.real = real;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}
}
