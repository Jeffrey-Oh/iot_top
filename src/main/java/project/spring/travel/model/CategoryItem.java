package project.spring.travel.model;

public class CategoryItem {
	private String value;
	private String text;

	public CategoryItem(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Category [value=" + value + ", text=" + text + "]";
	}

}
