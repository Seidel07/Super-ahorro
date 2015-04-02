package pages.transformer;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class TransformWebElement {
	
	public String toCategoryString(WebElement categoryElement) {
		return categoryElement.getText();
	}
	
	public List<String> toCategoryStringList(List<WebElement> categoryElementList) {
		List<String> categoryList = new ArrayList<String>();
		for (WebElement categoryElement : categoryElementList) {
			categoryList.add(this.toCategoryString(categoryElement));
		}
		return categoryList;
	}
	
}
